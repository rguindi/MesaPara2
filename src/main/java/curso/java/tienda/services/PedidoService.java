package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import curso.java.tienda.Entities.Pedido;
import curso.java.tienda.Repositories.PedidoRepository;


@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
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
	
	public void cambiarEstado (Long id, String estado) {
		pedidoRepository.cambiarEstado(id, estado);
	}
	

}
