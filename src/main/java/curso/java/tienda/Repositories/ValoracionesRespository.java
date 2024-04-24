package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import curso.java.tienda.Entities.Valoracion;

public interface ValoracionesRespository extends JpaRepository<Valoracion, Long> {
	
	@Query(value = "SELECT COUNT(*) " +
		       "FROM detalles_pedido d " +
		       "JOIN pedidos p ON d.id_pedido = p.id " +
		       "WHERE p.id_usuario = :userId AND d.id_producto = :productId", nativeQuery = true)
		Long countByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);


	@Query(value = "SELECT * FROM valoraciones WHERE id_usuario = :userId AND id_producto = :productId", nativeQuery = true)
	Long yaValorado(@Param("userId") Long userId, @Param("productId") Long productId);


	
	}
