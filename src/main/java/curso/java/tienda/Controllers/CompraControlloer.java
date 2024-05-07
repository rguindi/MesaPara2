package curso.java.tienda.Controllers;



import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import curso.java.tienda.Entities.Pedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.services.CarritoService;
import curso.java.tienda.services.CompraServicio;
import curso.java.tienda.services.PedidoService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CompraControlloer {
	
	@Autowired
	CompraServicio compraServicio;
	
	
	@Autowired
	CarritoService carritoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PedidoService pedidoService;
	
	
	@GetMapping("/procesarCompra")
	public String editar(HttpServletRequest request, Model model) {
		
		if(!usuarioService.clienteIsLoged(request)) return "redirect:/login";
		if(request.getSession().getAttribute("carrito")==null) return "redirect:/";
		
		//Si no hay Stock regeneramos el stock en el carrito y volvemos al resumen
		if(!compraServicio.modificarStock(request)) {
			HashMap<Producto, Integer> carrito = (HashMap<Producto, Integer>) request.getSession().getAttribute("carrito");
			carrito = compraServicio.regenerarStock(carrito);
			request.getSession().setAttribute("carrito", carrito);
			return "redirect:/resumenPedido";
		}
		
		Pedido pedido = compraServicio.generaPedido(request);
		pedidoService.guardar(pedido);
		compraServicio.generarLineas(request, pedido); 
		request.getSession().removeAttribute("metodo");
		request.getSession().removeAttribute("carrito");
		request.getSession().removeAttribute("cantidadTotal");
		request.getSession().removeAttribute("cupon");
		request.getSession().removeAttribute("subscribedProducts");

		return "compraRegistrada";
	}
	
	
	@PostMapping("/aplicarCodigo")
	public String aplicarCodigo(HttpSession miSesion, Model model, @RequestParam String cupon, RedirectAttributes attributes) {
		miSesion.setAttribute("cupon", cupon);
		String metodo = (String)miSesion.getAttribute("metodo");
		 attributes.addAttribute("metodo", metodo);
		 attributes.addAttribute("intento", "intentado");
		return "redirect:/resumenPedido";
		
	}
	

}
