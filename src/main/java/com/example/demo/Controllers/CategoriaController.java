package com.example.demo.Controllers;

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

import com.example.demo.Entities.Categoria;
import com.example.demo.Repositories.CategoriaRepository;

import jakarta.validation.Valid;

@Controller
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepositorio;
	
	
	@GetMapping("/categorias")
	public String listadoCategorias(Model model) {
		model.addAttribute("pag", "categoria");
		model.addAttribute("listaCategorias", categoriaRepositorio.findAll());
		return "/admin/categorias";
	}
	
	
	@GetMapping("/registrarCategoria")
	public String registarCategoria(Model model) {
		
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
			
		categoriaRepositorio.save(categoria);			
			return "redirect:/categorias";
		}
	}
	
	@GetMapping("/editarCategoria/{id}")
	public String editarCategoria(@PathVariable Long id, Model model) {
	
		Categoria categoria = categoriaRepositorio.findById(id).orElse(null);
		
		model.addAttribute("categoriaForm", categoria);
		return "registrarCategoria";
	}
	
	
	@PostMapping("/editarCategoria/submit")
	public String editarCategoriaSubmit(@Valid @ModelAttribute("categoriaForm") Categoria categoria,  BindingResult validacion) {
		
		if(validacion.hasErrors()) return "registrarCategoria";
		else {
    
			categoriaRepositorio.save(categoria);
			
			return "redirect:/categorias";
		}
	}
	
	@PostMapping("/gestionCategoria")
	public String egstionCategoria(@RequestParam String action, @RequestParam Long id) {
		
		if (action.equals("editar")) {
			return "redirect:/editarCategoria/" + id;
	    } else if (action.equals("eliminar")) {
	    	try {
	        categoriaRepositorio.deleteById(id);
	    	} catch (DataIntegrityViolationException e) {
	            System.out.println("No se puede eliminar la categoría porque otras tablas tienen filas que hacen referencia a ella");
	        
	    }
		
			
		}
		return "redirect:/categorias";
	
	}

}
