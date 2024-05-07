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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	UsuarioService usuarioService;
	
	@Autowired
	Opcion_menuService menServ;
	
	

	@GetMapping("/empleados")
	public String empleados(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request)  && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		//model.addAttribute("pag", "empleado");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(3));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/empleados";
	}
	
	@GetMapping("/administradores")
	public String administradores(Model model, HttpServletRequest request) {
		if(!usuarioService.superAdminIsLoged(request)) return "redirect:/";
	//	model.addAttribute("pag", "admin");
		model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(1));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/administradores";
	}
	
	
	@GetMapping("/clientes")
	public String clientes(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
	//	model.addAttribute("pag", "cliente");
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
			return "redirect:/editar/"+ user.getId();
		}
	}
	
	@PostMapping("/login/submit")
	public String loginSubmit(@RequestParam("user") String user, @RequestParam("pass") String pass, HttpServletRequest request, Model model) {
		
		if(!usuarioService.validarLogin(user, model, pass)) return "login";  //Validamos los campos
		if(!usuarioService.comprobarLogin(user, model, pass)) return "login";  //Comprobamos credenciales
		else {
			Usuario usuario = usuarioService.buscarPorEmail(user);	
			request.getSession().setAttribute("usuario", usuario);
			if(usuarioService.superAdminLogedAndDefaultPass(request)) {  //Comprobar si el administrador tiene la contraseña por defecto
				return "cambioForzosoDePass";
			}
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
	
	@PostMapping("/cambioForzoso")
	public String cambioForzoso(Model model, HttpSession miSesion,  @RequestParam("pass") String clave,  @RequestParam("rpass") String clave2, HttpServletRequest request) {
		//Validamos que es superAdmin
		if(!usuarioService.superAdminLogedAndDefaultPass(request)) return "redirect:/";
		
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
		
		if(clave.isEmpty()) {
			model.addAttribute("clave", "La clave no puede estar vacía");
			return "cambioForzosoDePass";
		}
		if(clave2.isEmpty()) {
			model.addAttribute("clave2", "La clave no puede estar vacía");
			return "cambioForzosoDePass";
		}
		
		if(!clave.equals(clave2)) {
			model.addAttribute("nocoinciden", "Las claves no coinciden");
			return "cambioForzosoDePass";
		}
		
		usuario.setClave(usuarioService.encriptar(clave));
		usuarioService.guardar(usuario);
		return "redirect:/home";
	}
	
	
	
	@PostMapping("/gestionEmpleado")
	public String gestionEmpleado( RedirectAttributes redirectAttributes, @RequestParam("id") Long id, @RequestParam("accion") String accion, HttpServletRequest request) {

		if(!usuarioService.adminIsLoged(request)&& !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(accion.equals("activar")) {
			usuarioService.activarUsuario(id);
			return "redirect:/empleados";
		}
		
		if(accion.equals("eliminar")) {
			usuarioService.desactivarUsuario(id);
			return "redirect:/empleados";
		}
		if(accion.equals("editar")) {
			redirectAttributes.addFlashAttribute("id", id);
			return "redirect:/editarUser";
		}
		return "redirect:/empleados";
	}
	
	@PostMapping("/gestionAdministrador")
	public String gestionAdministrador( RedirectAttributes redirectAttributes, @RequestParam("id") Long id, @RequestParam("accion") String accion, HttpServletRequest request) {

		if(!usuarioService.superAdminIsLoged(request)) return "redirect:/";

		if(accion.equals("activar")) {
			usuarioService.activarUsuario(id);
			return "redirect:/administradores";
		}
		
		if(accion.equals("eliminar")) {
			usuarioService.desactivarUsuario(id);
			return "redirect:/administradores";
		}
		if(accion.equals("editar")) {
			redirectAttributes.addFlashAttribute("id", id);
			return "redirect:/editarUser";
		}
		return "redirect:/administradores";
	}
	
	@PostMapping("/gestionCliente")
	public String gestionCliente(@RequestParam("id") Long id, @RequestParam("accion") String accion, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";


		if(accion.equals("activar")) {
			usuarioService.activarUsuario(id);
			return "redirect:/clientes";
		}
		
		if(accion.equals("eliminar")) {
			usuarioService.desactivarUsuario(id);
			return "redirect:/clientes";
		}
		
		if(accion.equals("editar")) {
			redirectAttributes.addFlashAttribute("id", id);
			return "redirect:/editarUser";
		}
		return "redirect:/clientes";
	}
	
	
	@GetMapping("/editarUser")
	public String editarCliente(Model model, @SessionAttribute("usuario") Usuario usuarioSesion, HttpServletRequest request) {
		
		Long id = (Long)model.getAttribute("id");
		Usuario usuario = usuarioService.buscarPorId(id);
		//Validamos quién y a quién esta editando
				if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
				if(usuarioService.empleadoIsLoged(request)&&usuario.getId_rol()!=2)return "redirect:/home";
				if(usuarioService.adminIsLoged(request)&&(usuario.getId_rol()!=2 && usuario.getId_rol()!=3))return "redirect:/home";
				
		
		
		model.addAttribute("usuarioForm", usuario);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/editarUsuarios";
	}
	
	@PostMapping("/editarUsuario")
	public String editarUsuario(Model model, @Valid @ModelAttribute("usuarioForm") Usuario user,  BindingResult validacion, HttpSession miSesion, HttpServletRequest request) {
		Usuario usuarioViejo = usuarioService.buscarPorId(user.getId());	
		//Validamos quién y a quién está editando
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		if(usuarioService.empleadoIsLoged(request)&&usuarioViejo.getId_rol()!=2)return "redirect:/home";
		if(usuarioService.adminIsLoged(request)&&(usuarioViejo.getId_rol()!=2 && usuarioViejo.getId_rol()!=3))return "redirect:/home";
		
			
		user.setId_rol(usuarioViejo.getId_rol());    
		if(!usuarioService.validarEdicion(user, validacion)) {
			Usuario user2 = (Usuario) request.getSession().getAttribute("usuario");
			List<Opciones_menu> opciones = menServ.opcinesPorRol(user2.getId_rol());
			model.addAttribute("opciones", opciones);
			return "/admin/editarUsuarios";
		}
		else {
			
			usuarioService.guardar(user);
			
			//Redirigimos en funcion del rol que estemos editando
			if(user.getId_rol()==1)return "redirect:/administradores";
			if(user.getId_rol()==3)return "redirect:/empleados";
			else return "redirect:/clientes";
		}
	}
	
	
	@GetMapping("/editarPass")
	public String editarPass(@RequestParam("id") Long id, HttpServletRequest request, Model model) {
		Usuario usuarioViejo = usuarioService.buscarPorId(id);	
		//Validamos quién y a quién está editando
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		if(usuarioService.empleadoIsLoged(request)&&usuarioViejo.getId_rol()!=2)return "redirect:/home";
		if(usuarioService.adminIsLoged(request)&&(usuarioViejo.getId_rol()!=2 && usuarioViejo.getId_rol()!=3))return "redirect:/home";
		
		model.addAttribute("id", id);
		Usuario user2 = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user2.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/editarPass";
	}
	
	
	@PostMapping("/editarPassSubmit")
	public String editarPassSubmit(@RequestParam("id") Long id, @RequestParam String clave,  @RequestParam String clave2, HttpServletRequest request, Model model) {
		Usuario usuarioViejo = usuarioService.buscarPorId(id);	
		//Validamos quién y a quién está editando
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		if(usuarioService.empleadoIsLoged(request)&&usuarioViejo.getId_rol()!=2)return "redirect:/home";
		if(usuarioService.adminIsLoged(request)&&(usuarioViejo.getId_rol()!=2 || usuarioViejo.getId_rol()!=3))return "redirect:/home";

		model.addAttribute("id", id);
		Usuario user2 = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user2.getId_rol());
		model.addAttribute("opciones", opciones);
		
		if(!clave.equals(clave2)) {
			model.addAttribute("errores", "Las claves no coinciden");
			return "/admin/editarPass";
		}
		if(clave.isEmpty()||clave2.isEmpty()) {
			model.addAttribute("errores", "Debe rellenar los campos");
			return "/admin/editarPass";
		}
		
		String encriptada = usuarioService.encriptar(clave);
		usuarioViejo.setClave(encriptada);
		usuarioService.guardar(usuarioViejo);
		
		//Redirigimos en funcion de lo que estemos editando
		if(usuarioViejo.getId_rol()==1)return "redirect:/administradores";
		if(usuarioViejo.getId_rol()==3)return "redirect:/empleados";
		else return "redirect:/clientes";
	}
	
	
	@GetMapping("/nuevoUsuario")
	public String nuevoUsuario(@RequestParam("rol") Long rol, HttpServletRequest request, Model model) {
		
		//Validamos quién y que rol se va a crear
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		if(usuarioService.empleadoIsLoged(request)&& rol!=2)return "redirect:/home";
		if(usuarioService.adminIsLoged(request)&&(rol!=2 && rol!=3))return "redirect:/home";
		
		model.addAttribute("rol", rol);
		Usuario user2 = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user2.getId_rol());
		model.addAttribute("opciones", opciones);
		model.addAttribute("usuarioForm", new Usuario());
		
		return "/admin/registrarUsuario";
	}
	
	@PostMapping("/nuevoUsuarioSubmit")
	public String nuevoUsuarioSubmit(HttpServletRequest request, @Valid @ModelAttribute("usuarioForm") Usuario user, BindingResult validacion,  @RequestParam("clave2") String clave2, @RequestParam String id_rol) {
		int id_rolInt = Integer.parseInt(id_rol);
		user.setId_rol(id_rolInt);
		//Validamos quién y a quién está editando
				if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
				if(usuarioService.empleadoIsLoged(request)&&user.getId_rol()!=2)return "redirect:/home";
				if(usuarioService.adminIsLoged(request)&&(user.getId_rol()!=2 && user.getId_rol()!=3))return "redirect:/home";
				
				 if(!usuarioService.validarRegistro(user, validacion, clave2)) return "/admin/registrarUsuario";
					else {
						
						user.setClave(usuarioService.encriptar(user.getClave()));
						usuarioService.guardar(user);
						//Redirigimos en funcion de lo que estemos editando
						if(user.getId_rol()==1)return "redirect:/administradores";
						if(user.getId_rol()==3)return "redirect:/empleados";
						else return "redirect:/clientes";
					}
				
	}
    
	
	
}
