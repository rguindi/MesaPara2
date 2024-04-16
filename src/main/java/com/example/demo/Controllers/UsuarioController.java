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
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.UsuarioRepository;
import com.example.demo.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
//import com.example.demo.Servicios.UsuarioServicio;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Autowired
	private UsuarioService usuarioService;
	
	

	@GetMapping("/empleados")
	public String empleados(Model model) {
		model.addAttribute("listaUsuarios", usuarioRepositorio.findAll());
		return "/admin/empleados";
	}
	
	@GetMapping("/clientes")
	public String clientes(Model model) {
		model.addAttribute("listaUsuarios", usuarioRepositorio.findAll());
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
			user.setId_rol(1);
			usuarioRepositorio.save(user);
			return "redirect:/login";
		}
	}
	
	
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model, @SessionAttribute("usuario") Usuario usuarioSesion) {
		
		
		if(usuarioSesion.getId() != id) return "redirect:/editar/" + usuarioSesion.getId();
		
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
			usuarioRepositorio.save(user);
			return "redirect:/";
		}
	}
	
	@PostMapping("/login/submit")
	public String loginSubmit(@RequestParam("user") String user, @RequestParam("pass") String pass, HttpServletRequest request, Model model) {
		
		if(!usuarioService.validarLogin(user, model, pass)) return "login";
		if(!usuarioService.comprobarLogin(user, model, pass)) return "login";
		else {
			Usuario usuario = usuarioRepositorio.findByEmail(user);	
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
	
	
	
	
}
