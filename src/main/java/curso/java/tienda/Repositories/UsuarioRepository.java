package curso.java.tienda.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.java.tienda.Entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.id_rol = :idRol")
    List<Usuario> findByRol(int idRol);
	
	Optional<Usuario> findByEmailAndFechaBajaIsNull(String email);

}
