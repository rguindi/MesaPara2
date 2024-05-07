package curso.java.tienda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.Repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepositorio;
	
	public boolean validarRegistro (Usuario user, BindingResult validacion,  @RequestParam("clave2") String clave2) {
		if(!user.getClave().equals(clave2)) validacion.rejectValue("clave", "error.clave.diferente", "Las contraseñas no coinciden");
        Usuario usuarioExistente = usuarioRepositorio.findByEmail(user.getEmail());
        if (!validacion.hasErrors() && usuarioExistente != null) {	
        	validacion.rejectValue("email", "error.email.existente", "Este email ya está registrado");
        }
        if(validacion.hasErrors()) return false;
        else return true;
		
	}
	

	
	
	public boolean validarEdicion (Usuario user, BindingResult validacion) {
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
	
	public boolean clienteIsLoged(HttpServletRequest request) {
		if(request.getSession().getAttribute("usuario") == null) return false;
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		if (usuario.getId_rol()== 2)return true;
		
		
		return false;

	}
	
	public boolean adminIsLoged(HttpServletRequest request) {
		if(request.getSession().getAttribute("usuario") == null) return false;
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		if (usuario.getId_rol()== 1)return true;
		
		
		return false;

	}
	
	public boolean empleadoIsLoged(HttpServletRequest request) {
		if(request.getSession().getAttribute("usuario") == null) return false;
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		if (usuario.getId_rol()== 3)return true;
		
		
		return false;

	}
	
	public boolean superAdminIsLoged(HttpServletRequest request) {
		if(request.getSession().getAttribute("usuario") == null) return false;
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		if(this.comprobarClaveEncriptada("admin", usuario.getClave())) return false;
		if (usuario.getId_rol()== 4 && usuario.getEmail().equals("superAdmin@superAdmin.com"))return true;
		return false;
	}
	
	public boolean superAdminLogedAndDefaultPass(HttpServletRequest request) {
		if(request.getSession().getAttribute("usuario") == null) return false;
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		if(this.comprobarClaveEncriptada("admin", usuario.getClave()) && usuario.getId_rol()== 4 && usuario.getEmail().equals("superAdmin@superAdmin.com")) return true;
		
		return false;
	}
	
	public List<Usuario> buscarPorRol(int rol){
		return usuarioRepositorio.findByRol(rol);
	}
	
	public Usuario buscarPorEmail(String email){
		return usuarioRepositorio.findByEmail(email);
	}
	
	public void guardar(Usuario usuario){
		 usuarioRepositorio.save(usuario);
	}
	
	public Usuario buscarPorId(Long id){
		return usuarioRepositorio.findById(id).orElse(null);
	}
	
}
