package curso.java.tienda.Controllers;

import java.util.List;

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

import curso.java.tienda.Entities.Categoria;
import curso.java.tienda.Entities.Configuracion;
import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.services.CategoriaService;
import curso.java.tienda.services.ConfiguracionService;
import curso.java.tienda.services.Opcion_menuService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class ConfiguracionController {
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	Opcion_menuService menServ;
	
	@GetMapping("/configuracion")
	public String listadoConfiguracion(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";

		model.addAttribute("listaConfiguracion", configuracionService.recuperarConfiguracion());
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/configuracion";
	}
	

	
	@PostMapping("/guardar")
	public String editarCategoria(@RequestParam Long id, @RequestParam String clave,@RequestParam String valor, Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		
		Configuracion  conf = new Configuracion(id, clave, valor, null);
		configuracionService.guardar(conf);
		

		return "redirect:/configuracion";
	}
	
	
	
}
