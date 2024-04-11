package com.example.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.UsuarioRepository;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;
//import com.example.demo.Servicios.UsuarioServicio;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Autowired
	private UsuarioService usuarioService;
	
	

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
	public String registroSubmit(@Valid @ModelAttribute("usuarioForm") Usuario user, BindingResult validacion,  @RequestParam("clave2") String clave2) {
        if(!usuarioService.validarRegistro(user, validacion, clave2)) return "registro";
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
	public String editarSubmit(@Valid @ModelAttribute("usuarioForm") Usuario user,  BindingResult validacion,  @RequestParam("clave2") String clave2) {
		
		if(!user.getClave().equals(clave2)) validacion.rejectValue("clave", "error.clave.diferente", "Las contraseñas no coinciden");
		if(!usuarioService.validarEdicion(user, validacion, clave2)) return "registro";
		else {
			user.setId_rol(1);
			//usuarioRepositorio.delete(user);      
			usuarioRepositorio.save(user);
			return "redirect:/usuarios";
		}
	}
	
	
}
