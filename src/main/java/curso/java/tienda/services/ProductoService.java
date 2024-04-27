package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Repositories.ProductoRepository;


@Service
public class ProductoService {
	@Autowired
	private ProductoRepository productoRepositorio;
	
	
	
	public Producto recuperarProducto (Long Id) {
		Producto producto = productoRepositorio.findById(Id).orElse(null);
		return producto;
		
	}
	
	public List <Producto> Ultimos12 () {
		
		return productoRepositorio.findTop12ByFechaBajaIsNullAndStockGreaterThanOrderByFechaAltaDesc(0);
		
	}
	
	public List <Producto> porCategoria (Long id) {
		
		return productoRepositorio.findByCategoriaAndFechaBajaIsNull(id);
		
	}
	
public List <Producto> todos () {
		
		return productoRepositorio.findAll();
		
	}

public void guardar (Producto producto) {
	
	 productoRepositorio.save(producto);
	
}

public void borradoLogico (Long id) {
	
	 productoRepositorio.softDeleteById(id);
	
}

public void reactivar (Long id) {
	
	 productoRepositorio.reactivateById(id); 
	
}

public List <Producto> buscar (String busca) {
	
	return productoRepositorio.findByNombreOrDescripcionContainingIgnoreCase(busca);
	
}

public List <Producto> masValorados () {
	
	return productoRepositorio.findTop12ByNumValoracionesOrderByNumValoracionesDescAndFechaBajaIsNull();
	
}

public List <Producto> masVendidos () {
	
	return productoRepositorio.findTop12ByUnidadesVendidasOrderByUnidadesVendidasDescAndFechaBajaIsNull();
}
	
	
	
	

}
