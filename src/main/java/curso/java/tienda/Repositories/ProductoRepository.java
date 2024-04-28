package curso.java.tienda.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import curso.java.tienda.Entities.Producto;


public interface ProductoRepository extends JpaRepository<Producto, Long>  {
	
	List<Producto> findTop12ByFechaBajaIsNullAndStockGreaterThanOrderByFechaAltaDesc(int stock);


	@Query("SELECT p FROM Producto p WHERE p.id_categoria = :categoriaId AND p.fechaBaja IS NULL")
    List<Producto> findByCategoriaAndFechaBajaIsNull(@Param("categoriaId") Long categoriaId);
	

	@Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.fechaBaja = CURRENT_TIMESTAMP WHERE p.id = :productoId")
    void softDeleteById(@Param("productoId") Long productoId);
	
	 @Transactional
	    @Modifying
	    @Query("UPDATE Producto p SET p.fechaBaja = null WHERE p.id = :productoId")
	    void reactivateById(@Param("productoId") Long productoId);
	 
	 @Query("SELECT p FROM Producto p WHERE lower(p.nombre) LIKE lower(concat('%', :texto, '%')) OR lower(p.descripcion) LIKE lower(concat('%', :texto, '%'))")
	    List<Producto> findByNombreOrDescripcionContainingIgnoreCase(@Param("texto") String texto);
	
	 

	    @Query(value = "SELECT p.* FROM productos p LEFT JOIN (SELECT id_producto, COUNT(*) AS num_valoraciones FROM valoraciones GROUP BY id_producto ORDER BY num_valoraciones DESC LIMIT 12) v ON p.id = v.id_producto WHERE p.fecha_baja IS NULL ORDER BY COALESCE(v.num_valoraciones, 0) DESC", nativeQuery = true)
	    List<Producto> findTop12ByNumValoracionesOrderByNumValoracionesDescAndFechaBajaIsNull();

	 
	 @Query(value = "SELECT p.* FROM productos p INNER JOIN (SELECT id_producto, SUM(unidades) AS total_unidades_vendidas FROM detalles_pedido GROUP BY id_producto ORDER BY total_unidades_vendidas DESC LIMIT 12) dp ON p.id = dp.id_producto WHERE p.fecha_baja IS NULL", nativeQuery = true)
	    List<Producto> findTop12ByUnidadesVendidasOrderByUnidadesVendidasDescAndFechaBajaIsNull();


	 @Transactional
	    @Modifying
	    @Query("UPDATE Producto p SET p.stock = p.stock + :cantidad WHERE p.id = :productoId")
	    void modificarStock(@Param("productoId") Long productoId, @Param("cantidad") int cantidad);

}
