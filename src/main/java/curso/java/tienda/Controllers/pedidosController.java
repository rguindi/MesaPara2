package curso.java.tienda.Controllers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import curso.java.tienda.Entities.DetallePedido;
import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Pedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.services.CarritoService;
import curso.java.tienda.services.DetalleService;
import curso.java.tienda.services.Opcion_menuService;
import curso.java.tienda.services.PedidoService;
import curso.java.tienda.services.ProductoService;
import curso.java.tienda.services.UsuarioService;
import curso.java.tienda.services.ValoracionService;
import curso.java.tienda.services.pdfService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	
	@Autowired
	ValoracionService valSer;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	Opcion_menuService menServ;
	
	@Autowired
	pdfService pdfservicio;
	
	
	
	@GetMapping("/verFactura")
	public ResponseEntity<ByteArrayResource>  generarPDF(@RequestParam Long id, HttpSession miSesion) {

		Pedido pedido = pedidoService.porId(id);
		Usuario user = usuarioService.buscarPorId(pedido.getId_usuario());
		
		//Validamos que somos el usuario que ha hecho el pedido
		Usuario usuarioSesion= (Usuario) miSesion.getAttribute("usuario");
		if(usuarioSesion == null || usuarioSesion.getId() != user.getId()) {
			return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/")
                    .build();
		}

        byte[] pdfBytes = pdfservicio.generarPDF(user, pedido);	
        
        // Configurar las cabeceras de la respuesta HTTP para indicar que es un archivo PDF para descargar
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "factura.pdf");
        
        // Crear un ByteArrayResource a partir del array de bytes del PDF generado
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        // Devolver la respuesta con el archivo adjunto (factura.pdf)
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
	}
	
	@GetMapping("/pedidos")
	public String pedidos(Model model, HttpServletRequest request) {
		if(!usuarioService.adminIsLoged(request) && !usuarioService.empleadoIsLoged(request) && !usuarioService.superAdminIsLoged(request)) return "redirect:/";
		//model.addAttribute("pag", "pedido");
		model.addAttribute("listaPedidos", pedidoService.todos());
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		List<Opciones_menu> opciones = menServ.opcinesPorRol(user.getId_rol());
		model.addAttribute("opciones", opciones);
		return "/admin/controlPedidos";
	}
	
	
	
	@GetMapping("/misPedidos")
	public String misPedidos(Model model, @SessionAttribute("usuario") Usuario usuario,  @RequestParam(name = "rango", required = false) String rango) {
		if (rango != null) {
			model.addAttribute("listaPedidos", pedidoService.porUsuarioYRango(usuario.getId(), rango, model));
				}else {
			model.addAttribute("listaPedidos", pedidoService.porUsuario(usuario.getId()));
		}
		
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
				if(valSer.esValorado(usuario.getId(), producto.getId())) model.addAttribute(producto.getNombre(), "valorado"); //Si el producto ya ha sido valorado informamos al modelo
			}
			
			model.addAttribute("productos", productos);
			Pedido pedido = pedidoService.porId(id);
			model.addAttribute("pedido", pedido);
			double totalSinIva = carritoServicio.TotalSinIva(productos);
			model.addAttribute("totalSinIva", totalSinIva);
			
			return "/detallePedido";
		}
		return "/misPedidos";
	}
	
	
	@PostMapping("/cambiarEstado")
	public String cancelarLinea( @RequestParam("id") Long id,  @RequestParam("estado") String estado, HttpServletRequest request) {
		pedidoService.cambiarEstado(id, estado, request);
		return "redirect:/pedidos";
	}
	
	@PostMapping("/cancelarLinea")
	public String cambiarEstado(Model model, @RequestParam Long idProducto,  @RequestParam Long idPedido, HttpServletRequest request) {
	 Usuario user = (Usuario) request.getSession().getAttribute("usuario");
	 Pedido pedido = pedidoService.porId(idPedido);
	 if(pedido.getId_usuario() != user.getId()) return "redirect:/";
	 	
	 pedidoService.borrarLinea(idProducto, idPedido);
	 
		//
	 model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		List<DetallePedido> detalles = detalleService.porIdPedido(idPedido);
		HashMap <Producto,Integer> productos = new HashMap<Producto, Integer>(); 
		for (DetallePedido detallePedido : detalles) {
			Producto producto = (Producto) productoService.recuperarProducto(detallePedido.getId_producto());
			productos.put(producto, detallePedido.getUnidades());
			if(valSer.esValorado(user.getId(), producto.getId())) model.addAttribute(producto.getNombre(), "valorado"); //Si el producto ya ha sido valorado informamos al modelo
		}
		
		model.addAttribute("productos", productos);
		model.addAttribute("pedido", pedido);
		double totalSinIva = carritoServicio.TotalSinIva(productos);
		model.addAttribute("totalSinIva", totalSinIva);
		
		return "/detallePedido";
	}

}
