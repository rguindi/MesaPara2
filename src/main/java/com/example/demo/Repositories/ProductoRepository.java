package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entities.Producto;


public interface ProductoRepository extends JpaRepository<Producto, Long>  {
	
	List<Producto> findTop12ByFechaBajaIsNullOrderByFechaAltaDesc();


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
	
}
