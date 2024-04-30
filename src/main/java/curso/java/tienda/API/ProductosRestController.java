package curso.java.tienda.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.java.tienda.Entities.Producto;
import curso.java.tienda.services.ProductoService;

@RestController
@RequestMapping("/API")
public class ProductosRestController {
	@Autowired
	ProductoService productoService;

	@GetMapping("/productos")
    public List<Producto> obtenerTodosLosUsuarios() {
        return productoService.todos();
	}
	
}
