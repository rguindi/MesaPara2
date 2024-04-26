package curso.java.tienda.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import curso.java.tienda.Entities.Pedido;
import jakarta.transaction.Transactional;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	 @Query("SELECT p FROM Pedido p WHERE p.id_usuario = :idUsuario ORDER BY p.id DESC")
	    List<Pedido> buscarPorIdUsuario(Long idUsuario);
	 
	 List<Pedido> findAllByOrderByIdDesc();
	 
	 @Query("UPDATE Pedido p SET p.estado = 'PC' WHERE p.id = :idPedido")
	    @Modifying
	    @Transactional
	    void cambiarEstadoAPC(Long idPedido);
	 
	 @Query("UPDATE Pedido p SET p.estado = :nuevoEstado WHERE p.id = :idPedido")
	 @Modifying
	 @Transactional
	 void cambiarEstado(Long idPedido, String nuevoEstado);
	 
	 
	 @Query("SELECT p FROM Pedido p WHERE p.id_usuario = :idUsuario AND p.fecha >= TIMESTAMPADD(DAY, -30, CURRENT_TIMESTAMP)  ORDER BY p.id DESC")
	    List<Pedido> buscarPorIdUsuarioYUltimos30Dias(Long idUsuario);

	 @Query("SELECT p FROM Pedido p WHERE p.id_usuario = :idUsuario AND p.fecha >= TIMESTAMPADD(DAY, -90, CURRENT_TIMESTAMP) ORDER BY p.id DESC")
	    List<Pedido> buscarPorIdUsuarioYUltimos90Dias(Long idUsuario);


	 @Query("SELECT p FROM Pedido p WHERE p.id_usuario = :idUsuario AND p.fecha >= TIMESTAMPADD(DAY, -365, CURRENT_TIMESTAMP)  ORDER BY p.id DESC")
	    List<Pedido> buscarPorIdUsuarioYUltimos365Dias(Long idUsuario);

}
