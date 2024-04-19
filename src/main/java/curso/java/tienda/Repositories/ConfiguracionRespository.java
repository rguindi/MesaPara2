package curso.java.tienda.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Configuracion;

public interface ConfiguracionRespository extends JpaRepository<Configuracion, Long> {
	
	Optional<Configuracion> findFirstByClave(String clave);

}
