package curso.java.tienda.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Rol;

public interface Opciones_menuRespository extends JpaRepository<Opciones_menu, Long> {
	
	@Query(value = "SELECT * FROM opciones_menu WHERE id_rol = :rol", nativeQuery = true)
    List<Opciones_menu> findRol(int rol);
}
