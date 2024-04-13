package com.example.demo.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.Entities.Pedido;
import com.example.demo.Repositories.PedidoRepository;
import com.example.demo.services.CarritoService;
import com.example.demo.services.CompraServicio;


import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CompraControlloer {
	
	@Autowired
	private CompraServicio compraServicio;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	CarritoService carritoService;
	
	
	
	@GetMapping("/procesarCompra")
	public String editar(HttpServletRequest request) {
		if(!carritoService.userIsLoged(request)) return "redirect:/login";
		
		//Pendiente controlar Stock
		
		
		Pedido pedido = compraServicio.generaPedido(request);
		pedidoRepository.save(pedido);
		compraServicio.generarLineas(request, pedido);
		 
		request.getSession().removeAttribute("carrito");
		request.getSession().removeAttribute("cantidadTotal");


		return "compraRegistrada";
	}
	

}
