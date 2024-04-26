package curso.java.tienda.Controllers;


import java.util.List;

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

import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.Repositories.UsuarioRepository;
import curso.java.tienda.services.Opcion_menuService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
//import com.example.demo.Servicios.UsuarioServicio;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	Opcion_menuService menServ;
	
	

	@GetMapping("/empleados")
	public String empleados(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request)  && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		model.addAttribute("pag", "empleado");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(3));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/empleados";
	}
	
	@GetMapping("/administradores")
	public String administradores(Model model, HttpServletRequest request) {
		if(!usuarioService.superAdminIsLoged(request)) return "redirect:/";
		model.addAttribute("pag", "admin");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(1));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/administradores";
	}
	
	
	@GetMapping("/clientes")
	public String clientes(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		model.addAttribute("pag", "cliente");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(2));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/clientes";
	}
	
	
	
	@GetMapping("/registro")
	public String registro(Model model,  HttpServletRequest request) {
		if(request.getSession().getAttribute("usuario") != null) return "redirect:/home";
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
		
		//Validamos que no se acceda al usuario equivocado
		if(usuarioSesion.getId() != id) return "redirect:/editar/" + usuarioSesion.getId();
		
		Usuario usuario = usuarioService.buscarPorId(id);
		model.addAttribute("usuarioForm", usuario);
		

		return "registro";
	}
	
	@GetMapping("/perfil")
	public String perfil(Model model, @SessionAttribute("usuario") Usuario usuarioSesion, HttpServletRequest request) {
		
		
		Usuario usuario = usuarioService.buscarPorId(usuarioSesion.getId());
		model.addAttribute("usuarioForm", usuario);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/perfil";
	}
	
	
	@GetMapping("/cambiarPass")
	public String cambiarPass( HttpServletRequest request) {	
		if(request.getSession().getAttribute("usuario") == null) return "redirect:/home";
		return "cambiarPass";
	}
	
	@PostMapping("/cambiarPass/submit")
	public String registroSubmit(Model model, HttpSession miSesion,  @RequestParam("opass") String old,  @RequestParam("pass") String clave,  @RequestParam("rpass") String clave2, HttpServletRequest request) {
		
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
		if (!usuarioService.comprobarClaveEncriptada(old, usuario.getClave())) {
			model.addAttribute("old", "La clave es incorrecta");
			return "cambiarPass";
		}
		if(clave.isEmpty()) {
			model.addAttribute("clave", "La clave no puede estar vacía");
			return "cambiarPass";
		}
		if(clave2.isEmpty()) {
			model.addAttribute("clave2", "La clave no puede estar vacía");
			return "cambiarPass";
		}
		
		if(!clave.equals(clave2)) {
			model.addAttribute("nocoinciden", "Las claves no coinciden");
			return "cambiarPass";
		}
		
		usuario.setClave(usuarioService.encriptar(clave));
		usuarioService.guardar(usuario);
		if(usuarioService.adminIsLoged(request)||usuarioService.empleadoIsLoged(request)||usuarioService.superAdminIsLoged(request)) return "redirect:/perfil";
		return "redirect:/";
	}
	
	
	
	
	@PostMapping("/editar/submit")
	public String editarSubmit(@Valid @ModelAttribute("usuarioForm") Usuario user,  BindingResult validacion, HttpSession miSesion, HttpServletRequest request) {
		Usuario usuarioViejo = usuarioService.buscarPorId(user.getId());	
		user.setId_rol(usuarioViejo.getId_rol());    
		if(!usuarioService.validarEdicion(user, validacion)) return "registro";
		else {
			
			usuarioService.guardar(user);
			miSesion.setAttribute("usuario", user);
			if(usuarioService.adminIsLoged(request)||usuarioService.empleadoIsLoged(request)||usuarioService.superAdminIsLoged(request)) return "redirect:/perfil";
			return "redirect:/";
		}
	}
	
	@PostMapping("/login/submit")
	public String loginSubmit(@RequestParam("user") String user, @RequestParam("pass") String pass, HttpServletRequest request, Model model) {
		
		if(!usuarioService.validarLogin(user, model, pass)) return "login";  //Validamos los campos
		if(!usuarioService.comprobarLogin(user, model, pass)) return "login";  //Comprobamos credenciales
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
        	
        	if(usuarioService.adminIsLoged(request)||usuarioService.empleadoIsLoged(request)||usuarioService.superAdminIsLoged(request)) return "redirect:/home";
            return "redirect:/";
        }
	}
	
	@PostMapping("/gestionEmpleado")
	public String gestionEmpleado(@RequestParam("id") Long id, @RequestParam("accion") String accion, HttpServletRequest request) {

		if(!usuarioService.adminIsLoged(request)&& !usuarioService.superAdminIsLoged(request)) return "redirect:/";

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
	
	@PostMapping("/gestionAdministrador")
	public String gestionAdministrador(@RequestParam("id") Long id, @RequestParam("accion") String accion, HttpServletRequest request) {

		if(!usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(accion.equals("activar")) {
			usuarioService.activarUsuario(id);
			return "redirect:/administradores";
		}
		
		if(accion.equals("eliminar")) {
			usuarioService.desactivarUsuario(id);
			return "redirect:/administradores";
		}
		return "redirect:/administradores";
	}
	
	@PostMapping("/gestionCliente")
	public String gestionCliente(@RequestParam("id") Long id, @RequestParam("accion") String accion, HttpServletRequest request) {

		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";


		if(accion.equals("activar")) {
			usuarioService.activarUsuario(id);
			return "redirect:/clientes";
		}
		
		if(accion.equals("eliminar")) {
			usuarioService.desactivarUsuario(id);
			return "redirect:/clientes";
		}
		return "redirect:/clientes";
	}
	
}
