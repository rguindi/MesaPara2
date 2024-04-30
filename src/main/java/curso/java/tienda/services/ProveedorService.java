package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Proveedor;
import curso.java.tienda.Repositories.ProveedorRepository;


@Service
public class ProveedorService {

	@Autowired
	private ProveedorRepository proveedorRepository;
	
	public Proveedor recuperarProveedor (Long Id) {
		Proveedor proveedor = proveedorRepository.findById(Id).orElse(null);
		return proveedor;
		
	}
	
	public List<Proveedor> recuperarProveedores () {
		List<Proveedor> proveedores = proveedorRepository.findAll();
		return proveedores;
		
	}
	
	public void guardar (Proveedor proveedor) {
		proveedorRepository.save(proveedor);
		
		
	}
	
	public void eliminar (Long id) {
		proveedorRepository.deleteById(id);
		
	}
}
