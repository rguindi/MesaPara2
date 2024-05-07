package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Descuento;
import curso.java.tienda.Repositories.DescuentosRepository;

@Service
public class DescuentosService {
	
	@Autowired
	DescuentosRepository descuentoRepository;
	
	public List<Descuento> descuentos(){
		
		List <Descuento> descuentos = descuentoRepository.findAll();
		
		return descuentos;
	}
	
	public void guardar (Descuento descuento) {
		descuentoRepository.save(descuento);
	}
	
	public void eliminar (Long id) {
		descuentoRepository.deleteById(id);
	}
	
	public Descuento descuentoPorId(Long id) {
		
		return descuentoRepository.findById(id).orElse(null);
	}
	
	public Descuento descuentoporCodigo(String codigo) {
		return descuentoRepository.findByCodigoAndFechaActualBetween(codigo);
	}

}
