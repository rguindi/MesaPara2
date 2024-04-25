package curso.java.tienda.Controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.Entities.Categoria;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Repositories.Opciones_menuRespository;
import curso.java.tienda.Repositories.RolRepository;
import curso.java.tienda.services.CategoriaService;
import curso.java.tienda.services.LoggingService;
import curso.java.tienda.services.MainService;
import curso.java.tienda.services.ProductoService;
import curso.java.tienda.services.ValoracionService;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {
	
	 
	
	 @Autowired
	    private MessageSource variables;
	
		@Autowired
		ProductoService productoService;
		
		@Autowired
		MainService mainService;
		
		@Autowired
		CategoriaService categoriaService;
	
		@Autowired
		RolRepository rolRepo;
		
		@Autowired
		Opciones_menuRespository menRepo;

		@Autowired
		LoggingService log;
		
		@Autowired
		ValoracionService valoracionService;
		
	
		
	@GetMapping("/")
	public String home(Model model) {
		
		List<Producto> novedades = productoService.Ultimos12();
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		List<Categoria> categorias = categoriaService.recuperarCategorias();
		model.addAttribute("categorias", categorias);
		model.addAttribute("novedades", novedades);
		log.logError("Probando Log-ERROR desde /");
		log.logInfo("Probando Log-INFO desde /");
		return "index";
	}
	
	@GetMapping("/esp")
	public String esp(HttpServletRequest request) {
		Map<String, String> spa = mainService.cargarPropertiesComoMapa("spanish");
		request.getSession().setAttribute("idioma", spa);
		return "redirect:/";
	}
	
	@GetMapping("/eng")
	public String eng(HttpServletRequest request) {
		Map<String, String> eng = mainService.cargarPropertiesComoMapa("english");
		request.getSession().setAttribute("idioma", eng);
		return "redirect:/";
	}

	
	@GetMapping("/login")
	public String login (Model model) {
		return "login";
	}
	
	@GetMapping("/contacto")
	public String contacto (Model model) {
		return "contacto";
	}
	
	@GetMapping("/about")
	public String about (Model model) {
		
		Map<String, String> datosEmpresa = mainService.datosEmpresa();
		model.addAttribute("nombre", datosEmpresa.get("nombre"));
		model.addAttribute("cif", datosEmpresa.get("cif"));
		model.addAttribute("direccion", datosEmpresa.get("direccion"));
		model.addAttribute("cp", datosEmpresa.get("cp"));
		model.addAttribute("poblacion", datosEmpresa.get("poblacion"));
		model.addAttribute("tlf", datosEmpresa.get("tlf"));
		return "about";
	}
	
	//Formulario de contacto
	@PostMapping("/contacto")
	public String contactoSubmit(Model model, @RequestParam("email") String email, @RequestParam("nombre") String nombre, @RequestParam("consulta") String consulta) {
		
		if(email.isBlank()||nombre.isBlank()||consulta.isBlank()) {
			   model = mainService.validar(email, nombre, consulta, model);
			return "contacto";
		}
		
		mainService.sendEmail(email, nombre, consulta);
		
		model.addAttribute("enviado", "Hemos recibido correctamente su consulta. En breve nos pondremos en contacto con usted. Muchas gracias.");
		
		return "contacto";
	}
	
	
	
	@GetMapping("/categoria/{categoria}")
	public String categoria(Model model, @PathVariable String categoria) {
		
		List<Producto> productos = productoService.porCategoria(Long.parseLong(categoria));
		Categoria categoriaActual = categoriaService.recuperarCategoria(Long.parseLong(categoria));
		List<Categoria> categorias = categoriaService.recuperarCategorias();
		model.addAttribute("categorias", categorias);
		model.addAttribute("categoriaActual", categoriaActual);
		model.addAttribute("productos", productos);
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		
		//Añadimos el numero de valoraciones y valoracion de cada producto al modelo
		for (Producto producto : productos) {
			Long valoraciones = valoracionService.numeroDeValoraciones(producto.getId());
			Double media = valoracionService.valoracionMedia(producto.getId());
			// Ajusta media a tramos de 0.5
			media = Math.ceil(media * 2) / 2.0;
			model.addAttribute("num"+producto.getId(), valoraciones);
			model.addAttribute("media"+producto.getId(), media);
		}
		return "productosCategoria";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}

}


