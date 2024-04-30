package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import curso.java.tienda.Entities.DetallePedido;
import curso.java.tienda.Entities.Pedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.Repositories.PedidoRepository;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CompraServicio compraServicio;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	DetalleService detalleService;
	
	@Autowired
	ProductoService productoService;
	
	
	public void guardar (Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
	public List<Pedido> todos () {
		return pedidoRepository.findAllByOrderByIdDesc();
	}
	
	public List<Pedido> porUsuario (Long id) {
		return pedidoRepository.buscarPorIdUsuario(id);
	}
	
	public void cambiarAPC (Long id) {
		pedidoRepository.cambiarEstadoAPC(id);
	}
	
	public Pedido porId (Long id) {
		return pedidoRepository.findById(id).orElse(null);
	}
	
	public  List<Pedido> porUsuarioYRango (Long id, String rango, Model model) {
		if(rango.equals("1mes")) {
			model.addAttribute("unMes", "si");
			return pedidoRepository.buscarPorIdUsuarioYUltimos30Dias(id);
		}
		else if (rango.equals("3meses")) {
			model.addAttribute("tresMmeses", "si");
			return pedidoRepository.buscarPorIdUsuarioYUltimos90Dias(id);
		}
			else if(rango.equals("12meses")) {
				model.addAttribute("doceMeses", "si");
				return pedidoRepository.buscarPorIdUsuarioYUltimos365Dias(id);
			}
			else {
				model.addAttribute("todos", "si");
				return pedidoRepository.buscarPorIdUsuario(id);
			}

	}
	
	public String cambiarEstado (Long id, String estado, HttpServletRequest request) {
		
		//Controlamos el accceso de cada usuario
		//Cliente
		if(usuarioService.clienteIsLoged(request)&& !estado.equals("PC")) return "redirect:/";
		//Empleado
		if(usuarioService.empleadoIsLoged(request) && !estado.equals("E")) return "redirect:/pedidos";
		Pedido pedido = this.porId(id);
		
		//Si ya se ha generado el numero no generamos otro.(si se ha generado ya no se puede cambiar estado)
		if(!pedido.getNum_factura().equals("En trámite")) return "redirect:/pedidos";
		
		//Si se cambia a Enviado generamos el numero de factura y lo guardamos, y mandamos un email a usuario.
		if(estado.equals("E")) {
			 String numFactura = compraServicio.generarNumFactura();
			 pedido.setNum_factura(numFactura);
			 guardar(pedido);
		//	 this.emailPedidoEnviado(pedido);    //Enviar email. se comenta la linea para el desarrollo 
		}
		
		if(estado.equals("C")) {
			 compraServicio.devolverStock(id);
		
		}
		
		
		pedidoRepository.cambiarEstado(id, estado);
		return "ok";
	}
	
	public void emailPedidoEnviado(Pedido pedido) {
		Usuario user = usuarioService.buscarPorId(pedido.getId_usuario());
		
		String to = user.getEmail();
		String subject = "Pedido " + pedido.getId() + " enviado.";
		String content = "Su pedido con número " + pedido.getId() + " ha salido de nuestras instalaciones. Pronto lo recibirá en su domicilio.";
		mainService.sendEmail(to, subject, content);
	}
	
	public void borrarLinea(Long idProducto, Long idPedido) {
		List<DetallePedido> lineas = detalleService.porIdPedido(idPedido);
		 int cantidad = 0;
		 Pedido pedido = this.porId(idPedido);
		 for (DetallePedido detallePedido : lineas) {
			 if(detallePedido.getId_producto() == idProducto) {
				 cantidad = detallePedido.getUnidades();
				 detalleService.borrar(detallePedido.getId());
				Producto producto = productoService.recuperarProducto(idProducto);
				producto.setStock(producto.getStock()+cantidad);
				productoService.guardar(producto);
				pedido.setTotal(pedido.getTotal()-detallePedido.getTotal());
				this.guardar(pedido);
			 }
			
		}
	}
	

}
