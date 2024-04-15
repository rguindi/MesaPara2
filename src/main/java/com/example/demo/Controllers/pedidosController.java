package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.PedidoRepository;

@Controller
public class pedidosController {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@GetMapping("/pedidos")
	public String pedidos(Model model) {
		
		model.addAttribute("listaPedidos", pedidoRepository.findAll());
		
		return "/admin/controlPedidos";
	}

}
