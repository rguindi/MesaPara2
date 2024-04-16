package com.example.demo.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Config.Global;
import com.example.demo.Entities.Categoria;
import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.CategoriaRepository;
import com.example.demo.Repositories.ProductoRepository;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {
	
	@Autowired
	private ProductoRepository productoRepositorio;
	
	@Autowired
	private CategoriaRepository categoriaRepositorio;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<Producto> novedades = productoRepositorio.findTop12ByFechaBajaIsNullOrderByFechaAltaDesc();
		
		model.addAttribute("IMG", Global.URL);
		
		List<Categoria> categorias = categoriaRepositorio.findAll();
		model.addAttribute("categorias", categorias);
		model.addAttribute("novedades", novedades);
		return "index";
	}

	
	@GetMapping("/login")
	public String login (Model model) {
		return "login";
	}
	
	
	@GetMapping("/categoria/{categoria}")
	public String categoria(Model model, @PathVariable String categoria) {
		
		List<Producto> productos = productoRepositorio.findByCategoriaAndFechaBajaIsNull(Long.parseLong(categoria));
		Categoria categoriaActual = categoriaRepositorio.findById(Long.parseLong(categoria)).orElse(null);
		List<Categoria> categorias = categoriaRepositorio.findAll();
		model.addAttribute("categorias", categorias);
		model.addAttribute("categoriaActual", categoriaActual);
		model.addAttribute("productos", productos);
		model.addAttribute("IMG", Global.URL);
		return "productosCategoria";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}

}


