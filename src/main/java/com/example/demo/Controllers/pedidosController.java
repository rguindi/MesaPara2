package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.Entities.Producto;
import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.PedidoRepository;

@Controller
public class pedidosController {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@GetMapping("/pedidos")
	public String pedidos(Model model) {
		model.addAttribute("pag", "pedido");
		model.addAttribute("listaPedidos", pedidoRepository.findAll());
		
		return "/admin/controlPedidos";
	}
	
	
	
	@GetMapping("/misPedidos")
	public String misPedidos(Model model, @SessionAttribute("usuario") Usuario usuario) {
		
		model.addAttribute("listaPedidos", pedidoRepository.buscarPorIdUsuario(usuario.getId()));
		
		return "/misPedidos";
	}
	
	@PostMapping("/gestionPedido")
	public String cancelarPedido(Model model, @SessionAttribute("usuario") Usuario usuario, @RequestParam("id") Long id, @RequestParam("gestion") String gestion) {
		
		if(gestion.equals("cancelar")) {
			pedidoRepository.cambiarEstadoAPC(id);
			model.addAttribute("listaPedidos", pedidoRepository.buscarPorIdUsuario(usuario.getId()));
			return "/misPedidos";
		}
		
		if(gestion.equals("ver")) {
			return "/detallePedido";
		}
		return "/misPedidos";
	}

}
