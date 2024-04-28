package curso.java.tienda.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Repositories.ProductoRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CarritoService {

	@Autowired
	ProductoRepository productorepository;

	public HashMap<Producto, Integer> recuperarCarrito(HttpServletRequest request) {
		HashMap<Producto, Integer> carrito = (HashMap<Producto, Integer>) request.getSession().getAttribute("carrito");
		if (carrito == null) {
			carrito = new HashMap<>();
			request.getSession().setAttribute("carrito", carrito);
		}

		return carrito;
	}

	public HashMap<Producto, Integer> addProducto(HashMap<Producto, Integer> carrito, Producto producto, int cantidad) {

		// Verificar si el producto ya está en el carrito
		if (carrito.containsKey(producto)) {
			int cantidadExistente = carrito.get(producto);
			carrito.put(producto, cantidadExistente + cantidad);
		} else {
			// Si el producto no está en el carrito, agregarlo con la cantidad proporcionada
			carrito.put(producto, cantidad);
		}

		return carrito;
	}

	public void contarCarrito(HashMap<Producto, Integer> carrito, HttpServletRequest request) {

		Integer cantidadTotal = 0;

		for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
			// Producto key = entry.getKey();
			Integer val = entry.getValue();
			cantidadTotal += val;
		}

		request.getSession().setAttribute("cantidadTotal", cantidadTotal);

	}

	public HashMap<Producto, Integer> actualizarProducto(HashMap<Producto, Integer> carrito, Long id, Integer cantidad,
			String modificar, String eliminar) {
		Producto producto = new Producto();

		if (id != null) {
			producto = productorepository.findById(id).orElse(null);
		}

		// comprobamos si estamos modificando la cantidad
		if (modificar != null) {

			carrito.put(producto, cantidad);
		}

		if (eliminar != null) {
			// System.out.println("Se ha pulsado eliminar");
			carrito.remove(producto);
		}
		return carrito;

	}
	

	
	public double TotalConIva(HashMap<Producto, Integer> carrito) {
		double cantidadTotal = 0;
		for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
			Producto key = entry.getKey();
			Integer val = entry.getValue();
			cantidadTotal += val * key.getPrecio();
		}
		return cantidadTotal;
	}
	
	public double TotalSinIva(HashMap<Producto, Integer> carrito) {
		double cantidadTotal = 0;
		for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
			Producto key = entry.getKey();
			Integer val = entry.getValue();
			Double sinIva = key.getPrecio() * (key.getImpuesto()/100);
			cantidadTotal += val * sinIva;
		}
		return cantidadTotal;
	}

	
	
}



