package curso.java.tienda.Controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.services.CarritoService;
import curso.java.tienda.services.ProductoService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class CarritoController {
	
	 @Autowired
	    private MessageSource variables;
	
	@Autowired
	CarritoService carritoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ProductoService productoService;
	
	
	@PostMapping("/addCarrito")
	public String addCarrito(@RequestParam("id") Long id, @RequestParam("cantidad") int cantidad, HttpServletRequest request, Model model) {
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		Producto producto = productoService.recuperarProducto(id);
		HashMap<Producto,Integer> carrito = carritoService.recuperarCarrito(request);
		carrito = carritoService.addProducto(carrito, producto, cantidad);
		model.addAttribute("carrito", carrito);
		carritoService.contarCarrito(carrito, request);
		model.addAttribute("producto", producto);
		model.addAttribute("cantidad", cantidad);

		return "addCarrito";
	
	}
	
	
	@GetMapping("/carrito")
	public String carrito(HttpServletRequest request, Model model,@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "cantidad", required = false) Integer cantidad, @RequestParam(value = "modificar", required = false) String modificar, @RequestParam(value = "eliminar", required = false) String eliminar) {
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		HashMap<Producto,Integer> carrito = carritoService.recuperarCarrito(request);
		carrito = carritoService.actualizarProducto(carrito, id, cantidad, modificar, eliminar);
		model.addAttribute("carrito", carrito);
		carritoService.contarCarrito(carrito, request);
		double totalConIva = carritoService.TotalConIva(carrito);
		model.addAttribute("totalConIva", totalConIva);
		double totalSinIva = carritoService.TotalSinIva(carrito);
		model.addAttribute("totalSinIva", totalSinIva);
		
		return "carrito";
	}
	
	@GetMapping("/comprar")
	public String comprar(HttpServletRequest request) {
		if(!usuarioService.clienteIsLoged(request)) {
			request.getSession().setAttribute("urlAnterior", "/carrito");
			return "redirect:/login";
		}
		
		return "metodoPago";
	}
	
	
	@GetMapping("/resumenPedido")
	public String resumenPedido(HttpServletRequest request, Model model, @RequestParam("metodo") String metodo) {
		
		if(!usuarioService.clienteIsLoged(request)) return "redirect:/login";
		model.addAttribute("metodo", metodo);
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		request.getSession().setAttribute("metodo", metodo);
		HashMap<Producto,Integer> carrito = carritoService.recuperarCarrito(request);
		model.addAttribute("carrito", carrito);
		double totalConIva = carritoService.TotalConIva(carrito);
		model.addAttribute("totalConIva", totalConIva);
		double totalSinIva = carritoService.TotalSinIva(carrito);
		model.addAttribute("totalSinIva", totalSinIva);
		
		return "resumenPedido";
	}
	
	
}
