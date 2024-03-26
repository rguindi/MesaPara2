package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entities.Producto;
import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.ProductoRepository;
import com.example.demo.Repositories.UsuarioRepository;

import jakarta.validation.Valid;

public class ProductoController {
	
	@Autowired
	private ProductoRepository productoRepositorio;
	
	@GetMapping("/productos")
	public String listadoProductos(Model model) {
		
		model.addAttribute("listaProductos", productoRepositorio.findAll());
		return "usuarios";
	}
	
	
	@GetMapping("/registrarProducto")
	public String registarProducto(Model model) {
		
		model.addAttribute("productoForm", new Producto());
		return "registrarProducto";
	}
	
	@PostMapping("/registrarProducto/submit")
	public String registrarProductoSubmit(@Valid @ModelAttribute("productoForm") Producto producto, BindingResult validacion) {
		
		if(validacion.hasErrors()) {
			return "registrarProducto";
		}
		else {
			
			
			productoRepositorio.save(producto);			
			return "redirect:/productos";
		}
	}
	
	@GetMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id, Model model) {
	
		Producto producto = productoRepositorio.findById(id).orElse(null);
		
		model.addAttribute("productoForm", producto);
		return "registrarProducto";
	}
	
	
	@PostMapping("/editarProducto/submit")
	public String editarProductoSubmit(@Valid @ModelAttribute("productoForm") Producto producto,  BindingResult validacion) {
		
		if(validacion.hasErrors()) return "registrarProducto";
		else {
			
			productoRepositorio.delete(producto);      
			productoRepositorio.save(producto);
			
			return "redirect:/usuarios";
		}
	}
	

}
