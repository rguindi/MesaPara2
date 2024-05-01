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

	 
	 @Query(value = "SELECT\n"
	 		+ "    CASE\n"
	 		+ "        WHEN MONTH(fecha) = 1 THEN 'Enero'\n"
	 		+ "        WHEN MONTH(fecha) = 2 THEN 'Febrero'\n"
	 		+ "        WHEN MONTH(fecha) = 3 THEN 'Marzo'\n"
	 		+ "        WHEN MONTH(fecha) = 4 THEN 'Abril'\n"
	 		+ "        WHEN MONTH(fecha) = 5 THEN 'Mayo'\n"
	 		+ "        WHEN MONTH(fecha) = 6 THEN 'Junio'\n"
	 		+ "        WHEN MONTH(fecha) = 7 THEN 'Julio'\n"
	 		+ "        WHEN MONTH(fecha) = 8 THEN 'Agosto'\n"
	 		+ "        WHEN MONTH(fecha) = 9 THEN 'Septiembre'\n"
	 		+ "        WHEN MONTH(fecha) = 10 THEN 'Octubre'\n"
	 		+ "        WHEN MONTH(fecha) = 11 THEN 'Noviembre'\n"
	 		+ "        WHEN MONTH(fecha) = 12 THEN 'Diciembre'\n"
	 		+ "    END AS mes,\n"
	 		+ "    SUM(total) AS total_pedidos\n"
	 		+ "FROM pedidos\n"
	 		+ "WHERE fecha <= CURDATE()\n"
	 		+ "    AND fecha >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)\n"
	 		+ "GROUP BY MONTH(fecha)\n"
	 		+ "ORDER BY\n"
	 		+ "    (MONTH(CURDATE()) - MONTH(fecha) + 12) % 12 + 1;\n"
	 		+ "",
     nativeQuery = true)
	 List<Object[]> obtenerFacturacionUltimos6Meses();


}


