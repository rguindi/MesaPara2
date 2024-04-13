package com.example.demo.Controllers;



import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.Entities.Pedido;
import com.example.demo.Entities.Producto;
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
	public String editar(HttpServletRequest request, Model model) {
		if(!carritoService.userIsLoged(request)) return "redirect:/login";
		
		
		//Si no hay Stock regeneramos el stock en el carrito y volvemos al resumen
		if(!compraServicio.modificarStock(request)) {
			HashMap<Producto, Integer> carrito = (HashMap<Producto, Integer>) request.getSession().getAttribute("carrito");
			carrito = compraServicio.regenerarStock(carrito);
			model.addAttribute("errorStock", "Error al revisar el Stock del producto. Compruebe su cesta.");
			return "redirect:/resumenPedido";
		}
		
		Pedido pedido = compraServicio.generaPedido(request);
		pedidoRepository.save(pedido);
		compraServicio.generarLineas(request, pedido);
		 
		request.getSession().removeAttribute("carrito");
		request.getSession().removeAttribute("cantidadTotal");


		return "compraRegistrada";
	}
	

}
