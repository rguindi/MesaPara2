package curso.java.tienda.Repositories;

import java.util.List;

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
	
	
	@Query(value = "SELECT COALESCE(ROUND(AVG(valoracion), 1), 0) FROM valoraciones WHERE id_producto = :idProducto", nativeQuery = true)
	Double obtenerValoracionMediaDecimalPorIdProducto(@Param("idProducto") Long idProducto);


	@Query(value = "SELECT COUNT(*) FROM valoraciones WHERE id_producto = :idProducto", nativeQuery = true)
	Long countByProductId(@Param("idProducto") Long idProducto);
	
	 @Query("SELECT p FROM Valoracion p WHERE p.id_producto = :idProducto")
	    List<Valoracion> findByProductoId(@Param("idProducto") Long idProducto);

	 
	 
	 
	 
		@Query(value = "SELECT id_producto, COUNT(*) AS veces_repetido\n"
				+ "FROM valoraciones\n"
				+ "GROUP BY id_producto LIMIT 6", nativeQuery = true)
		 List<Object[]> obtener6MasValorados();
	


	
	}
