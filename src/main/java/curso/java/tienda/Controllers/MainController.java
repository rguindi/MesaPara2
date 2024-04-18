package curso.java.tienda.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import curso.java.tienda.Entities.Categoria;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.services.CategoriaService;
import curso.java.tienda.services.ProductoService;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {
	
	 @Autowired
	    private MessageSource variables;
	
		@Autowired
		ProductoService productoService;
		
		@Autowired
		CategoriaService categoriaService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<Producto> novedades = productoService.Ultimos12();
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		List<Categoria> categorias = categoriaService.recuperarCategorias();
		model.addAttribute("categorias", categorias);
		model.addAttribute("novedades", novedades);
		return "index";
	}

	
	@GetMapping("/login")
	public String login (Model model) {
		return "login";
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
		return "productosCategoria";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}

}


