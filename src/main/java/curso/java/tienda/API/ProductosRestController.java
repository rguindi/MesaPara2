package curso.java.tienda.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<Producto> obtenerTodosLosProductoss() {
        return productoService.todos();
	}
	
	// Obtener un producto por su ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.recuperarProducto(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo producto
    @PostMapping("/productos")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    // Actualizar un producto existente
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoExistente = productoService.recuperarProducto(id);
        if (productoExistente != null) {
            producto.setId(id); // asegurarse de que el ID coincida
           productoService.guardar(producto);
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        Producto productoExistente = productoService.recuperarProducto(id);
        if (productoExistente != null) {
            productoService.borradoLogico(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
}
