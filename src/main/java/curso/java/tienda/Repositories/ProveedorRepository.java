package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Proveedor;


public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}
