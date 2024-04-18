package curso.java.tienda.Controllers;

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
import curso.java.tienda.services.CategoriaService;
import jakarta.validation.Valid;

@Controller
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	
	@GetMapping("/categorias")
	public String listadoCategorias(Model model) {
		model.addAttribute("pag", "categoria");
		model.addAttribute("listaCategorias", categoriaService.recuperarCategorias());
		return "/admin/categorias";
	}
	
	
	@GetMapping("/registrarCategoria")
	public String registarCategoria(Model model) {
		model.addAttribute("pag", "categoria");
		model.addAttribute("categoriaForm", new Categoria());
		return "registrarCategoria";
	}
	
	
	@PostMapping("/registrarCategoria/submit")
	public String registrarCategoriaSubmit(@Valid @ModelAttribute("categoriaForm") Categoria categoria, 
			BindingResult validacion) {
		
		if(validacion.hasErrors()) {
			return "registrarCategoria";
		}
		else {
			
		categoriaService.guardar(categoria);			
			return "redirect:/categorias";
		}
	}
	
	@GetMapping("/editarCategoria/{id}")
	public String editarCategoria(@PathVariable Long id, Model model) {
		model.addAttribute("pag", "categoria");
		Categoria categoria = categoriaService.recuperarCategoria(id);
		
		model.addAttribute("categoriaForm", categoria);
		return "/admin/registrarCategoria";
	}
	
	
	@PostMapping("/editarCategoria/submit")
	public String editarCategoriaSubmit(@Valid @ModelAttribute("categoriaForm") Categoria categoria,  BindingResult validacion) {
		
		if(validacion.hasErrors()) return "registrarCategoria";
		else {
    
			categoriaService.guardar(categoria);
			
			return "redirect:/categorias";
		}
	}
	
	@PostMapping("/gestionCategoria")
	public String egstionCategoria(@RequestParam String action, @RequestParam Long id) {
		
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
