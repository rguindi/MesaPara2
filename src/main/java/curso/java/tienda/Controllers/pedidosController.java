package curso.java.tienda.Controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import curso.java.tienda.Entities.DetallePedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.services.CarritoService;
import curso.java.tienda.services.DetalleService;
import curso.java.tienda.services.PedidoService;
import curso.java.tienda.services.ProductoService;

@Controller
public class pedidosController {
	
	@Autowired
    private MessageSource variables;
	
	@Autowired
	PedidoService pedidoService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	DetalleService detalleService;
	
	@Autowired
	CarritoService carritoServicio;
	
	@GetMapping("/pedidos")
	public String pedidos(Model model) {
		model.addAttribute("pag", "pedido");
		model.addAttribute("listaPedidos", pedidoService.todos());
		
		return "/admin/controlPedidos";
	}
	
	
	
	@GetMapping("/misPedidos")
	public String misPedidos(Model model, @SessionAttribute("usuario") Usuario usuario) {
		model.addAttribute("listaPedidos", pedidoService.porUsuario(usuario.getId()));
		return "/misPedidos";
	}
	
	
	
	@PostMapping("/gestionPedido")
	public String cancelarPedido(Model model, @SessionAttribute("usuario") Usuario usuario, @RequestParam("id") Long id, @RequestParam("gestion") String gestion) {
		
		if(gestion.equals("cancelar")) {
			pedidoService.cambiarAPC(id);
			model.addAttribute("listaPedidos", pedidoService.porUsuario(usuario.getId()));
			return "/misPedidos";
		}
		
		if(gestion.equals("ver")) {
			model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
			List<DetallePedido> detalles = detalleService.porIdPedido(id);
			HashMap <Producto,Integer> productos = new HashMap<Producto, Integer>(); 
			for (DetallePedido detallePedido : detalles) {
				Producto producto = (Producto) productoService.recuperarProducto(detallePedido.getId_producto());
				productos.put(producto, detallePedido.getUnidades());
				model.addAttribute("productos", productos);
			}
			String metodo = pedidoService.porId(id).getMetodo_pago();
			model.addAttribute("metodo", metodo);
			double totalConIva = carritoServicio.TotalConIva(productos);
			model.addAttribute("totalConIva", totalConIva);
			double totalSinIva = carritoServicio.TotalSinIva(productos);
			model.addAttribute("totalSinIva", totalSinIva);
			
			return "/detallePedido";
		}
		return "/misPedidos";
	}

}
