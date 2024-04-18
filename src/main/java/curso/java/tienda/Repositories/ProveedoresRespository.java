package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Valoracion;

public interface ProveedoresRespository extends JpaRepository<Valoracion, Long> {

}
