package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Configuracion;

public interface ValoracionesRespository extends JpaRepository<Configuracion, Long> {

}
