package com.example.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.UsuarioRepository;

import jakarta.validation.Valid;
//import com.example.demo.Servicios.UsuarioServicio;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;

	@GetMapping("/usuarios")
	public String listado(Model model) {
		
		model.addAttribute("listaUsuarios", usuarioRepositorio.findAll());
		return "usuarios";
	}
	
	@GetMapping("/registro")
	public String registro(Model model) {
		
		model.addAttribute("usuarioForm", new Usuario());
		return "registro";
	}
	
	@PostMapping("/registro/submit")
	public String registroSubmit(@Valid @ModelAttribute("usuarioForm") Usuario user, BindingResult validacion) {
		
		if(validacion.hasErrors()) {
			return "registro";
		}
		else {
			
			
			user.setId_rol(1);
			usuarioRepositorio.save(user);
			
			return "redirect:/usuarios";
		}
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
	
		Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
		
		model.addAttribute("usuarioForm", usuario);
		return "registro";
	}
	
	@PostMapping("/editar/submit")
	public String editarSubmit(@Valid @ModelAttribute("usuarioForm") Usuario user,  BindingResult validacion) {
		
		if(validacion.hasErrors()) return "registro";
		else {
			
			usuarioRepositorio.delete(user);      
			usuarioRepositorio.save(user);
			
			return "redirect:/usuarios";
		}
	}
	
	
	
}
