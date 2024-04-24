package curso.java.tienda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	

	
	

}
