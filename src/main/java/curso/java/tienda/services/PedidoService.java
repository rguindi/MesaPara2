package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	

}
