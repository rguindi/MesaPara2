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
import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.services.CategoriaService;
import curso.java.tienda.services.Opcion_menuService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	Opcion_menuService menServ;
	
	@GetMapping("/categorias")
	public String listadoCategorias(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		//model.addAttribute("pag", "categoria");
		model.addAttribute("listaCategorias", categoriaService.recuperarCategorias());
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/categorias";
	}
	
	
	@GetMapping("/registrarCategoria")
	public String registarCategoria(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		//model.addAttribute("pag", "categoria");
		model.addAttribute("categoriaForm", new Categoria());
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/registrarCategoria";
	}
	
	
	@PostMapping("/registrarCategoria/submit")
	public String registrarCategoriaSubmit(@Valid @ModelAttribute("categoriaForm") Categoria categoria, 
			BindingResult validacion, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(validacion.hasErrors()) {
			return "registrarCategoria";
		}
		else {
			
		categoriaService.guardar(categoria);			
			return "redirect:/categorias";
		}
	}
	
	@GetMapping("/editarCategoria/{id}")
	public String editarCategoria(@PathVariable Long id, Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
	//	model.addAttribute("pag", "categoria");
		Categoria categoria = categoriaService.recuperarCategoria(id);
		
		model.addAttribute("categoriaForm", categoria);
		return "/admin/registrarCategoria";
	}
	
	
	@PostMapping("/editarCategoria/submit")
	public String editarCategoriaSubmit(@Valid @ModelAttribute("categoriaForm") Categoria categoria,  BindingResult validacion,  HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(validacion.hasErrors()) return "registrarCategoria";
		else {
    
			categoriaService.guardar(categoria);
			
			return "redirect:/categorias";
		}
	}
	
	@PostMapping("/gestionCategoria")
	public String egstionCategoria(@RequestParam String action, @RequestParam Long id,  HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		if (action.equals("editar")) {
			return "redirect:/editarCategoria/" + id;
	    } else if (action.equals("eliminar")) {
	    	try {
	        categoriaService.eliminar(id);
	    	} catch (DataIntegrityViolationException e) {
	            System.out.println("No se puede eliminar la categoría porque otras tablas tienen filas que hacen referencia a ella");
	        
	    }
		
			
		}
		return "redirect:/categorias";
	
	}

}
