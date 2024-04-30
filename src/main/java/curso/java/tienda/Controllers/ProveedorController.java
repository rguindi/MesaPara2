package curso.java.tienda.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.Entities.Proveedor;
import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.services.ProveedorService;
import curso.java.tienda.services.Opcion_menuService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	Opcion_menuService menServ;
	
	@GetMapping("/proveedores")
	public String listadoProveedores(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		//model.addAttribute("pag", "proveedor");
		model.addAttribute("listaProveedores", proveedorService.recuperarProveedores());
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/proveedores";
	}
	
	
	@GetMapping("/registrarProveedor")
	public String registarProveedor(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		//model.addAttribute("pag", "proveedor");
		model.addAttribute("proveedorForm", new Proveedor());
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/registrarProveedor";
	}
	
	
	@PostMapping("/registrarProveedor/submit")
	public String registrarProveedorSubmit(@Valid @ModelAttribute("proveedorForm") Proveedor proveedor, 
			BindingResult validacion, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(validacion.hasErrors()) {
			return "/admin/registrarProveedor";
		}
		else {
			
		proveedorService.guardar(proveedor);			
			return "redirect:/proveedores";
		}
	}
	
	@GetMapping("/editarProveedor/{id}")
	public String editarProveedor(@PathVariable Long id, Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
	//	model.addAttribute("pag", "proveedor");
		Proveedor proveedor = proveedorService.recuperarProveedor(id);
		
		model.addAttribute("proveedorForm", proveedor);
		return "/admin/registrarProveedor";
	}
	
	
	@PostMapping("/editarProveedor/submit")
	public String editarProveedorSubmit(@Valid @ModelAttribute("proveedorForm") Proveedor proveedor,  BindingResult validacion,  HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(validacion.hasErrors()) return "registrarProveedor";
		else {
    
			proveedorService.guardar(proveedor);
			
			return "redirect:/proveedores";
		}
	}
	
	@PostMapping("/gestionProveedor")
	public String egstionProveedor(@RequestParam String action, @RequestParam Long id,  HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		if (action.equals("editar")) {
			return "redirect:/editarProveedor/" + id;
	    } else if (action.equals("eliminar")) {
	    	try {
	        proveedorService.eliminar(id);
	    	} catch (DataIntegrityViolationException e) {
	            System.out.println("No se puede eliminar el proveedor ");
	        
	    }
		
			
		}
		return "redirect:/proveedores";
	
	}

}
