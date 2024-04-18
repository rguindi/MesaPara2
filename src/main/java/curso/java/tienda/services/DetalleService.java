package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.DetallePedido;
import curso.java.tienda.Repositories.DetallePedidoRepository;

@Service
public class DetalleService {
	
	@Autowired
	private DetallePedidoRepository detalleRepositorio;
	
	public List<DetallePedido> porIdPedido(Long Id){
		return detalleRepositorio.buscaPedido(Id);
	}

}
