package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Impuesto;

public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

}
