package curso.java.tienda.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.Entities.Descuento;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.services.CarritoService;
import curso.java.tienda.services.DescuentosService;
import curso.java.tienda.services.ProductoService;
import curso.java.tienda.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class CarritoController {
	
	 @Autowired
	 MessageSource variables;
	
	@Autowired
	CarritoService carritoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	DescuentosService descuentoService;
	
	@Autowired
    SimpMessagingTemplate messagingTemplate;
	
	
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

		//Alerta a Websocket.   Funciona tanto la suscrición como la alerta cuando alguien añade la última unidad o todas las que quedan en stock.
		 if (producto != null && producto.getStock() == cantidad) {
			 System.out.println("Añádido ultimo  producto a carrito");
	            // El producto añadido es el último en stock, enviar alerta
	            String message = "¡Alguien ha añadido la última unidad de  '" + producto.getNombre() + "' a su carrito. Date prisa si no quieres perderla!";
	            messagingTemplate.convertAndSend("/topic/lastStockAlert/" + producto.getId(), message);
	            
	            List<Long> subscribedProducts = (List<Long>) request.getSession().getAttribute("subscribedProducts");
	            if (subscribedProducts == null) {
	                subscribedProducts = new ArrayList<>();
	                System.out.println("Lista de suscripciones creada");
	            }

	            // Suscribir al nuevo producto si aún no está en la lista
	            if (!subscribedProducts.contains(producto.getId())) {
	                subscribedProducts.add(producto.getId());
	                System.out.println("Añádido  producto a sesion para suscribirse");
	            }

	            // Actualizar la lista en la sesión
	            request.getSession().setAttribute("subscribedProducts", subscribedProducts);
	        }
		
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
	public String resumenPedido(HttpServletRequest request, Model model,  @RequestParam(value = "metodo", required = false) String metodo, @RequestParam(value = "intento", required = false) String intento) {
		
		if(!usuarioService.clienteIsLoged(request)) return "redirect:/login";
		if (metodo != null) {
			model.addAttribute("metodo", metodo);
			request.getSession().setAttribute("metodo", metodo);
		}else {																			//Si venimos de error al procesar compra no hay metodo. Lo recuperamos de la sesion.
			String metodo2 = (String) request.getSession().getAttribute("metodo");
			model.addAttribute("metodo", metodo2);
			model.addAttribute("errorStock", "El Stock ha sido modificado. Revise su carrito.");
		}
		

		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		HashMap<Producto,Integer> carrito = carritoService.recuperarCarrito(request);
		model.addAttribute("carrito", carrito);
		double totalConIva = carritoService.TotalConIva(carrito);
		model.addAttribute("totalConIva", totalConIva);
		double totalSinIva = carritoService.TotalSinIva(carrito);
		model.addAttribute("totalSinIva", totalSinIva);
		
		
		//Comprobamos cupon de descuento
				String cupon = (String) request.getSession().getAttribute("cupon");
				if (cupon==null) cupon = "";
				Descuento descuento = descuentoService.descuentoporCodigo(cupon);
		if(descuento != null) {
			model.addAttribute("descuento", descuento);
			model.addAttribute("totalConDescuento", totalConIva-descuento.getDescuento());
			model.addAttribute("totalConIva", totalConIva - descuento.getDescuento());
			System.out.println("Hay un codigo de descuento aplicado de " + descuento.getDescuento());
			
		}
		if (intento!=null && descuento == null)model.addAttribute("noValido", "Código no válido");
		
		return "resumenPedido";
	}
	
	
}
