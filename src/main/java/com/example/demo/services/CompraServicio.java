package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Configuracion;
import com.example.demo.Entities.DetallePedido;
import com.example.demo.Entities.Pedido;
import com.example.demo.Entities.Producto;
import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.ConfiguracionRespository;
import com.example.demo.Repositories.DetallePedidoRepository;
import com.example.demo.Repositories.ProductoRepository;

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

		Double total = 0.00;
		for (Entry<Producto, Integer> entry : carrito.entrySet()) {
			Producto key = entry.getKey();
			Integer val = entry.getValue();
			total += key.getPrecio() * val;
		}

		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		String metodoPago = (String) request.getSession().getAttribute("metodo");
		String numFactura;

		Configuracion configuracion = new Configuracion("", "", "Factura");
		configuracionRepository.save(configuracion);

		numFactura = "FAC" + configuracion.getId();

		Pedido pedido = new Pedido(usuario.getId(), new java.sql.Timestamp(System.currentTimeMillis()), metodoPago,
				"PE", numFactura, total);

		return pedido;

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
			
			if (producto == null || producto.getStock()<val) return false;
			
			producto.setStock(producto.getStock() - val);
			
			productoRepository.save(producto);
			
		}

		return true;
	}
	
	public HashMap<Producto, Integer> regenerarStock(HashMap<Producto, Integer> carrito) {
		
		for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
			Producto key = entry.getKey();
			Integer val = entry.getValue();

			Producto producto = productoRepository.findById(key.getId()).orElse(null);  
			key.setStock(producto.getStock());
			val = producto.getStock();

		}

		return carrito;

	}

}
