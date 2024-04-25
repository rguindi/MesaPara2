package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Valoracion;
import curso.java.tienda.Repositories.ValoracionesRespository;

@Service
public class ValoracionService {
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	ValoracionesRespository valRepos;
	
	public boolean esComprado (Long usuario, Long producto) {
		Long count = valRepos.countByUserIdAndProductId(usuario, producto);
		if (count==null) return false;
		if (count>=1) return true;
		else return false;
		
	}
	
	public boolean esValorado (Long usuario, Long producto) {
		
		Long count = valRepos.yaValorado(usuario, producto);
		if (count==null) return false;
		if (count>=1) return true;
		else return false;
		
	}
	
	public void guardar(Valoracion val) {
		
		valRepos.save(val);
		
	}
	
public double valoracionMedia(Long id) {
		
		return valRepos.obtenerValoracionMediaDecimalPorIdProducto(id);
		
	}

public Long numeroDeValoraciones(Long id) {
	
	return valRepos.countByProductId(id);
	
}

public List<Valoracion> todasPorIdProducto(Long id) {
	
	return (List<Valoracion>) valRepos.findByProductoId(id);
	
}
	
	

}
