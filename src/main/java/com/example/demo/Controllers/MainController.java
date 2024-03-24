package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("pruebaModelo", "Este texto se ha pasado desde el controlador al modelo.");
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
