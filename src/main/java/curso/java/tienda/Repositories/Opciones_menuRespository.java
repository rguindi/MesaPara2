package curso.java.tienda.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Entities.Rol;

public interface Opciones_menuRespository extends JpaRepository<Opciones_menu, Long> {
	
	 @Query("SELECT om FROM Opciones_menu om WHERE om.id_rol = ?1")
	    List<Opciones_menu> findByIdRol(Long rol);
}
