package curso.java.tienda.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Configuracion;
import curso.java.tienda.Entities.DetallePedido;
import curso.java.tienda.Entities.Pedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.Repositories.ConfiguracionRespository;
import curso.java.tienda.Repositories.DetallePedidoRepository;
import curso.java.tienda.Repositories.ProductoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class CompraServicio {

	@Autowired
	CarritoService carritoService;

	@Autowired
	ConfiguracionRespository configuracionRepository;
	
	@Autowired
	ProductoRepository productoRepository;

	@Autowired
	DetallePedidoRepository detallePedidoRepository;

	public Pedido generaPedido(HttpServletRequest request) {

		HashMap<Producto, Integer> carrito = carritoService.recuperarCarrito(request);

		Double total = carritoService.TotalConIva(carrito);


		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		String metodoPago = (String) request.getSession().getAttribute("metodo");
		
		String numFactura = "En trámite";
		
		Pedido pedido = new Pedido(usuario.getId(), new java.sql.Timestamp(System.currentTimeMillis()), metodoPago,
				"PE", numFactura, total);

		return pedido;

	}
	
	public String generarNumFactura() {
		String numFactura;
		Configuracion confFactura = configuracionRepository.findFirstByClave("factura").orElse(null);
		if(confFactura == null) {
			Configuracion configuracion = new Configuracion("factura", "1", "");
			configuracionRepository.save(configuracion);
			confFactura = configuracionRepository.findFirstByClave("factura").orElse(null);
		}
		

		int numero = Integer.parseInt(confFactura.getValor());
		numFactura = "FAC" + numero;
		numero++;
		confFactura.setValor(String.valueOf(numero));
		configuracionRepository.save(confFactura);
		return numFactura;
	}

	public void generarLineas(HttpServletRequest request, Pedido pedido) {
		HashMap<Producto, Integer> carrito = carritoService.recuperarCarrito(request);

		for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
			Producto key = entry.getKey();
			Integer val = entry.getValue();

			DetallePedido detalle = new DetallePedido(pedido.getId(), key.getId(),
					Double.valueOf(key.getPrecio()).floatValue(), val, key.getImpuesto(), key.getPrecio() * val);
			detallePedidoRepository.save(detalle);

		}

	}
	
	@Transactional
	public boolean modificarStock(HttpServletRequest request) {
		
		HashMap<Producto, Integer> carrito = carritoService.recuperarCarrito(request);
		
		for (Entry<Producto, Integer> entry : carrito.entrySet()) {
			Producto key = entry.getKey();
			Integer val = entry.getValue();
			
			Producto producto = productoRepository.findById(key.getId()).orElse(null);         //recuperamos de nuevo el producto para tener un stock actualizado en el proceso de compra
			
			if (producto == null || producto.getStock()<val || val<1) return false;
			
			producto.setStock(producto.getStock() - val);
			
			productoRepository.save(producto);
			
		}

		return true;
	}
	
	public HashMap<Producto, Integer> regenerarStock(HashMap<Producto, Integer> carrito) {
	    for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
	        Producto producto = entry.getKey();
	        Integer cantidad = entry.getValue();

	        Producto productoBD = productoRepository.findById(producto.getId()).orElse(null);
	        
	        // Verificar si el producto se encuentra en la base de datos
	        if (productoBD != null) {
	        	
	            // Actualizar el stock del producto en el carrito con el valor obtenido de la base de datos
	            producto.setStock(productoBD.getStock());
	            // Actualizar la cantidad en el carrito si es necesario
	            if (productoBD.getStock() < cantidad) {
	                carrito.put(producto, productoBD.getStock());
	            }
	            //Si aguien malintencionadamente pone un stock negativo ponemos en 1
	            if (productoBD.getStock() >0 && cantidad<0) {
	                carrito.put(producto, 1);
	            }
	            
	            
	            
	        } else {
	            // Si el producto no se encuentra en la base de datos, eliminarlo del carrito
	            carrito.remove(producto);
	        }
	    }
	    return carrito;
	}


}
