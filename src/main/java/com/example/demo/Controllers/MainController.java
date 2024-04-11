package com.example.demo.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Entities.Categoria;
import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.CategoriaRepository;
import com.example.demo.Repositories.ProductoRepository;


@Controller
public class MainController {
	
	@Autowired
	private ProductoRepository productoRepositorio;
	
	@Autowired
	private CategoriaRepository categoriaRepositorio;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<Producto> novedades = productoRepositorio.findTop12ByFechaBajaIsNullOrderByFechaAltaDesc();
		
		List<Categoria> categorias = categoriaRepositorio.findAll();
		model.addAttribute("categorias", categorias);
		model.addAttribute("novedades", novedades);
		return "index";
	}

	
	@GetMapping("/login")
	public String login (Model model) {
		return "login";
	}
	

	
}


