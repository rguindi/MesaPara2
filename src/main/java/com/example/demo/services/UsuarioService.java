package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	public boolean validarRegistro (Usuario user, BindingResult validacion,  @RequestParam("clave2") String clave2) {
		if(!user.getClave().equals(clave2)) validacion.rejectValue("clave", "error.clave.diferente", "Las contraseñas no coinciden");
        Usuario usuarioExistente = usuarioRepositorio.findByEmail(user.getEmail());
        if (!validacion.hasErrors() && usuarioExistente != null) {	
        	validacion.rejectValue("email", "error.email.existente", "Este email ya está registrado");
        }
        if(validacion.hasErrors()) return false;
        else return true;
		
	}
	
	
	public boolean validarEdicion (Usuario user, BindingResult validacion,  @RequestParam("clave2") String clave2) {
		if(!user.getClave().equals(clave2)) validacion.rejectValue("clave", "error.clave.diferente", "Las contraseñas no coinciden");
        if(validacion.hasErrors()) return false;
        else return true;
		
	}
	
	public boolean validarLogin (String user, Model model,  String clave) {
		if(user.isEmpty()) model.addAttribute("usuarioRequerido", "Campo requerido");
		if(clave.isEmpty()) model.addAttribute("claveRequerida", "Campo requerido");
        if(user.isEmpty()||clave.isEmpty()) return false;
        
        else return true;
		
	}
	
	public boolean comprobarLogin (String user, Model model,  String clave) {
		Usuario usuario = usuarioRepositorio.findByEmail(user);	
		if(usuario==null || !usuario.getClave().equals(clave)) {
			model.addAttribute("incorrecto", "Usuario o contraseña incorrecta");
			 return false;
		}
		
		
		
		return true;
		
	}
}