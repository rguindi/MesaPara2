package curso.java.tienda.Controllers;



import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import curso.java.tienda.Entities.Pedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.services.CarritoService;
import curso.java.tienda.services.CompraServicio;
import curso.java.tienda.services.PedidoService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CompraControlloer {
	
	@Autowired
	private CompraServicio compraServicio;
	
	
	@Autowired
	CarritoService carritoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PedidoService pedidoService;
	
	
	@GetMapping("/procesarCompra")
	public String editar(HttpServletRequest request, Model model) {
		
		if(!usuarioService.clienteIsLoged(request)) return "redirect:/login";
		
		//Si no hay Stock regeneramos el stock en el carrito y volvemos al resumen
		if(!compraServicio.modificarStock(request)) {
			HashMap<Producto, Integer> carrito = (HashMap<Producto, Integer>) request.getSession().getAttribute("carrito");
			carrito = compraServicio.regenerarStock(carrito);
			model.addAttribute("errorStock", "Error al revisar el Stock del producto. Compruebe su cesta.");
			return "redirect:/resumenPedido";
		}
		
		Pedido pedido = compraServicio.generaPedido(request);
		pedidoService.guardar(pedido);
		compraServicio.generarLineas(request, pedido); 
		request.getSession().removeAttribute("carrito");
		request.getSession().removeAttribute("cantidadTotal");

		return "compraRegistrada";
	}
	

}
