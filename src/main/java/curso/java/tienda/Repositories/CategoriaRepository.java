package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
