package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Categoria;
import curso.java.tienda.Entities.Configuracion;
import curso.java.tienda.Repositories.CategoriaRepository;
import curso.java.tienda.Repositories.ConfiguracionRespository;


@Service
public class ConfiguracionService {

	@Autowired
	ConfiguracionRespository configuracionRepository;
	
	
	public List<Configuracion> recuperarConfiguracion () {
		List<Configuracion> configuracion = configuracionRepository.findAll();
		return configuracion;
		
	}
	
	public void guardar (Configuracion configuracion) {
		configuracionRepository.save(configuracion);
		
		
	}

}
