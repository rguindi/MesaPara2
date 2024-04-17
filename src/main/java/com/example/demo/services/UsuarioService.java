package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.UsuarioRepository;
import org.jasypt.util.password.StrongPasswordEncryptor;

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
		Usuario usuario = usuarioRepositorio.findByEmailAndFechaBajaIsNull(user).orElse(null);	
		if(usuario==null || !this.comprobarClaveEncriptada(clave, usuario.getClave())) {
			model.addAttribute("incorrecto", "Usuario o contraseña incorrecta");
			 return false;
		}		
		return true;	
	}
	
	public boolean activarUsuario (Long id ) {
		Usuario usuario = usuarioRepositorio.findById(id).orElse(null);	
		usuario.setFechaBaja(null);
		usuarioRepositorio.save(usuario);
		return true;	
	}
	public boolean desactivarUsuario (Long id ) {
		Usuario usuario = usuarioRepositorio.findById(id).orElse(null);	
		usuario.setFechaBaja(new java.sql.Timestamp(System.currentTimeMillis()));
		usuarioRepositorio.save(usuario);
		return true;	
	}
	
	
	public String encriptar(String pass) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encriptada = passwordEncryptor.encryptPassword(pass);
		return encriptada;
	}
	
	public boolean comprobarClaveEncriptada(String pass, String passencriptado) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		if (passwordEncryptor.checkPassword(pass, passencriptado)) return true;
		else return false;
	}
	
}
