package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.ProductoRepository;

import jakarta.validation.Valid;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoRepository productoRepositorio;
	
	@GetMapping("/productos")
	public String listadoProductos(Model model) {
		
		model.addAttribute("listaProductos", productoRepositorio.findAll());
		return "productos";
	}
	
	
	@GetMapping("/registrarProducto")
	public String registarProducto(Model model) {
		
		model.addAttribute("productoForm", new Producto());
		return "registrarProducto";
	}
	
	@PostMapping("/registrarProducto/submit")
	public String registrarProductoSubmit(@Valid @ModelAttribute("productoForm") Producto producto, BindingResult validacion, @RequestParam("file") MultipartFile file) {
		
		if(validacion.hasErrors()) {
			return "registrarProducto";
		}
		else {
			
			if (!file.isEmpty()) {
				producto.setImagen(file.getOriginalFilename());
			}
			
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
