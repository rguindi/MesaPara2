package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

}
