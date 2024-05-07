package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Categoria;
import curso.java.tienda.Repositories.CategoriaRepository;


@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	public Categoria recuperarCategoria (Long Id) {
		Categoria categoria = categoriaRepository.findById(Id).orElse(null);
		return categoria;
		
	}
	
	public List<Categoria> recuperarCategorias () {
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias;
		
	}
	
	public void guardar (Categoria categoria) {
		categoriaRepository.save(categoria);
		
		
	}
	
	public void eliminar (Long id) {
		categoriaRepository.deleteById(id);
		
	}
}
