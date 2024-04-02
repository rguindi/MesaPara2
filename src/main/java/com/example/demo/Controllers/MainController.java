package com.example.demo.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.ProductoRepository;


@Controller
public class MainController {
	
	@Autowired
	private ProductoRepository productoRepositorio;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<Producto> novedades = productoRepositorio.findTop4ByOrderByFechaAltaDesc();
		
		model.addAttribute("novedades", novedades);
		return "index";
	}

	
	@GetMapping("/login")
	public String login (Model model) {
		return "login";
	}
	
	
	
	
	
}

/*
	  @GetMapping("/requestParam") public String req (@RequestParam (name = "name",
	  required = false, defaultValue = "Lo que sea") String name, Model model) {
	  model.addAttribute("name", name); return "login"; }
	  
	  @GetMapping("/requestParam") public String req (@RequestParam ("name")
	  Optional <String> name, Model model) { model.addAttribute("name",
	  name.orElse("Lo que sea")); return "login"; }
	  
	  @GetMapping("/{name}") public String req (@PathVariable ("name") String name,
	  Model model) { model.addAttribute("name", name); return "login"; }
	   
 */
