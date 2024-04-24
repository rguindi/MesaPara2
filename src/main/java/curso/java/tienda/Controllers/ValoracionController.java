package curso.java.tienda.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.services.ProductoService;
import curso.java.tienda.services.ValoracionService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ValoracionController {
	
	@Autowired
    private MessageSource variables;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	ValoracionService valoracionService;
	
	@GetMapping("/valorar/{id}")
	public String valorar(Model model, @PathVariable Long id, HttpServletRequest request) {	
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		//Comprobamos si el usuario ha comprado el producto
		if(!valoracionService.esComprado(usuario.getId(), id))return "redirect:/";

		if(valoracionService.esValorado(usuario.getId(), id))return "redirect:/";
		
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		Producto producto = productoService.recuperarProducto(id);
		model.addAttribute("producto", producto);
		
		return "valorar";
	}

}
