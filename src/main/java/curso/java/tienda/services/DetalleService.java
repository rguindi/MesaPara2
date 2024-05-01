package curso.java.tienda.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.DetallePedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.DTO.Facturacion;
import curso.java.tienda.Entities.DTO.ProductoVentas;
import curso.java.tienda.Repositories.DetallePedidoRepository;

@Service
public class DetalleService {
	
	@Autowired
	private DetallePedidoRepository detalleRepositorio;
	
	@Autowired
	private ProductoService productoServicio;
	
	public List<DetallePedido> porIdPedido(Long Id){
		return detalleRepositorio.buscaPedido(Id);
	}
	
	public void borrar(Long Id){
		detalleRepositorio.deleteById(Id);
	}
	
	public List<ProductoVentas>  masVendidos () {
		List<ProductoVentas> ventas = new ArrayList<>();
		List<Object[]> resultados = detalleRepositorio.obtener6MasVendidos();
		
		for (Object[] resultado : resultados) {
			Long productoId = ((Number) resultado[0]).longValue();
			Producto pro = productoServicio.recuperarProducto(productoId);
            String nombre = pro.getNombre();
            
            Integer cantidad = ((Number) resultado[1]).intValue();
            
            // Crear objeto Facturacion y agregar a la lista
            ProductoVentas PV = new ProductoVentas(nombre, cantidad);
            ventas.add(PV);
        }
        
        return ventas;
	}

}
