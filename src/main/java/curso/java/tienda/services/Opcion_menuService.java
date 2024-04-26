package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Opciones_menu;
import curso.java.tienda.Repositories.Opciones_menuRespository;

@Service
public class Opcion_menuService {

	@Autowired
	Opciones_menuRespository opRep;
	
	public List<Opciones_menu> opcinesPorRol(int rol){
		
		return opRep.findRol(rol);
	}
}
