package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Descuento;

public interface DescuentosRepository extends JpaRepository<Descuento, Long> {

}
