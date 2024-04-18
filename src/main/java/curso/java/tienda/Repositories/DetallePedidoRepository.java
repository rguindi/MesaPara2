package curso.java.tienda.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.java.tienda.Entities.DetallePedido;


public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
	
	
	@Query("SELECT d FROM DetallePedido d WHERE d.id_pedido = :id_pedido")
    List<DetallePedido> buscaPedido(Long id_pedido);

}
