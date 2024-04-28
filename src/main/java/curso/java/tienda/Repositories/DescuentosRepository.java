package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import curso.java.tienda.Entities.Descuento;

public interface DescuentosRepository extends JpaRepository<Descuento, Long> {
	
	
	@Query(value= "SELECT * FROM descuentos d WHERE d.codigo = :codigo AND CURRENT_TIMESTAMP BETWEEN d.fecha_inicio AND d.fecha_fin ORDER BY d.id ASC LIMIT 1", nativeQuery = true)
	Descuento findByCodigoAndFechaActualBetween(@Param("codigo") String codigo);

}
