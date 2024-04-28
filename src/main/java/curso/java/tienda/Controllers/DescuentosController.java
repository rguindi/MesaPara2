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

import curso.java.tienda.Entities.Categoria;
import curso.java.tienda.Entities.Descuento;
import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.services.DescuentosService;
import curso.java.tienda.services.Opcion_menuService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class DescuentosController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	DescuentosService descuentoService;
	
	@Autowired
	Opcion_menuService menServ;

	@GetMapping("/cupones")
	public String cupones(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		model.addAttribute("listaDescuentos", descuentoService.descuentos());
		
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/descuentos";
	}
	
	
	@GetMapping("/registrarDescuento")
	public String registarCategoria(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		
		model.addAttribute("descuentoForm", new Descuento());
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/registrarDescuento";
	}
	
	@GetMapping("/editarDescuento/{id}")
	public String editarDescuento(@PathVariable Long id, Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		Descuento descuento = descuentoService.descuentoPorId(id);
		
		model.addAttribute("descuentoForm", descuento);
		return "/admin/registrarDescuento";
	}
	
	@PostMapping("/editarDescuento/submit")
	public String editarDescuentoSubmit(Model model, @Valid @ModelAttribute("descuentoForm") Descuento descuento,  BindingResult validacion,  HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(validacion.hasErrors()) {
			Usuario user = (Usuario) request.getSession().getAttribute("usuario");
			List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
			model.addAttribute("opciones", opciones);
			return "/admin/registrarDescuento";
		}
		else {
    
			descuentoService.guardar(descuento);
			
			return "redirect:/cupones";
		}
	}
	
	@PostMapping("/registrarDescuento/submit")
	public String registrarDescuentoSubmit(@Valid @ModelAttribute("descuentoForm") Descuento descuento, 
			BindingResult validacion, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(validacion.hasErrors()) {
			return "/admin/registrarDescuento";
		}
		else {
			
		descuentoService.guardar(descuento);			
			return "redirect:/cupones";
		}
	}

	@PostMapping("/gestionDescuentos")
	public String gestionDescuentos(@RequestParam String action, @RequestParam Long id,  HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		if (action.equals("editar")) {
		return "redirect:/editarDescuento/" + id;
	    } else if (action.equals("eliminar")) {
	    	
	        descuentoService.eliminar(id);
	    	}
		return "redirect:/cupones";
	
	}
	
}
