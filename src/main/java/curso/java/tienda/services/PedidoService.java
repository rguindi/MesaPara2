package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import curso.java.tienda.Entities.Pedido;
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
	
	public void guardar (Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
	public List<Pedido> todos () {
		return pedidoRepository.findAll();
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
	
		//Si se cambia a Enviado generamos el numero de factura y lo guardamos
		Pedido pedido = this.porId(id);
		if(estado.equals("E")) {
			 String numFactura = compraServicio.generarNumFactura();
			 pedido.setNum_factura(numFactura);
			 guardar(pedido);
		}
		pedidoRepository.cambiarEstado(id, estado);
		return "ok";
	}
	

}
