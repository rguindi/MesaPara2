package curso.java.tienda.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.Repositories.UsuarioRepository;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
//import com.example.demo.Servicios.UsuarioServicio;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	

	@GetMapping("/empleados")
	public String empleados(Model model) {
		model.addAttribute("pag", "empleado");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(3));
		return "/admin/empleados";
	}
	
	@GetMapping("/administradores")
	public String administradores(Model model) {
		model.addAttribute("pag", "admin");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(1));
		return "/admin/administradores";
	}
	
	
	@GetMapping("/clientes")
	public String clientes(Model model) {
		model.addAttribute("pag", "cliente");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(2));
		return "/admin/clientes";
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
			user.setId_rol(2);
			user.setClave(usuarioService.encriptar(user.getClave()));
			usuarioService.guardar(user);
			return "redirect:/login";
		}
	}
	
	
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model, @SessionAttribute("usuario") Usuario usuarioSesion) {
		
		
		if(usuarioSesion.getId() != id) return "redirect:/editar/" + usuarioSesion.getId();
		
		Usuario usuario = usuarioService.buscarPorId(id);
		model.addAttribute("usuarioForm", usuario);

		return "registro";
	}
	
	
	
	@PostMapping("/editar/submit")
	public String editarSubmit(@Valid @ModelAttribute("usuarioForm") Usuario user,  BindingResult validacion,  @RequestParam("clave2") String clave2) {
		
		if(!user.getClave().equals(clave2)) validacion.rejectValue("clave", "error.clave.diferente", "Las contraseñas no coinciden");
		if(!usuarioService.validarEdicion(user, validacion, clave2)) return "registro";
		else {
			user.setId_rol(1);    
			user.setClave(usuarioService.encriptar(user.getClave()));
			usuarioService.guardar(user);
			return "redirect:/";
		}
	}
	
	@PostMapping("/login/submit")
	public String loginSubmit(@RequestParam("user") String user, @RequestParam("pass") String pass, HttpServletRequest request, Model model) {
		
		if(!usuarioService.validarLogin(user, model, pass)) return "login";
		if(!usuarioService.comprobarLogin(user, model, pass)) return "login";
		else {
			Usuario usuario = usuarioService.buscarPorEmail(user);	
			request.getSession().setAttribute("usuario", usuario);
		}
		
		String urlAnterior = (String) request.getSession().getAttribute("urlAnterior");
        if (urlAnterior != null) {
            // Redirigir al usuario de vuelta a la URL original después de iniciar sesión correctamente
            request.getSession().removeAttribute("urlAnterior"); // Limpiar la URL original de la sesión
            return "redirect:" + urlAnterior;
        } else {
            // Si no hay URL original guardada, redirigir al usuario a una página predeterminada
            return "redirect:/";
        }
	}
	
	@PostMapping("/gestionEmpleado")
	public String gestionEmpleado(@RequestParam("id") Long id, @RequestParam("accion") String accion) {

		
		if(accion.equals("activar")) {
			usuarioService.activarUsuario(id);
			return "redirect:/empleados";
		}
		
		if(accion.equals("eliminar")) {
			usuarioService.desactivarUsuario(id);
			return "redirect:/empleados";
		}
		return "redirect:/empleados";
	}
	
	
}
