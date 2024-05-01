package curso.java.tienda.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Valoracion;
import curso.java.tienda.Entities.DTO.ProductoVentas;
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

public List<ProductoVentas> masValorados() {
	List<ProductoVentas> valorados = new ArrayList<>();
	List<Object[]> resultados = valRepos.obtener6MasValorados();
	
	for (Object[] resultado : resultados) {
		Long productoId = ((Number) resultado[0]).longValue();
		Producto pro = productoService.recuperarProducto(productoId);
        String nombre = pro.getNombre();
        
        Integer cantidad = ((Number) resultado[1]).intValue();
        
        // Crear objeto Facturacion y agregar a la lista
        ProductoVentas PV = new ProductoVentas(nombre, cantidad);
        valorados.add(PV);
    }
    
	return  valorados;
	
}
	
	

}
