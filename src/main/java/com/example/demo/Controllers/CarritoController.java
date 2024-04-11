package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.ProductoRepository;
import com.example.demo.services.CarritoService;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class CarritoController {
	
	@Autowired
	ProductoRepository productorepository;
	
	@Autowired
	CarritoService carritoService;
	
	
	@PostMapping("/addCarrito")
	public String addCarrito(@RequestParam("id") Long id, @RequestParam("cantidad") int cantidad, HttpServletRequest request, Model model) {
		
		Producto producto = productorepository.findById(id).orElse(null);
		HashMap<Producto,Integer> carrito = carritoService.recuperarCarrito(request);
		carrito = carritoService.addProducto(carrito, producto, cantidad);
		model.addAttribute("carrito", carrito);
		carritoService.contarCarrito(carrito, request);
		model.addAttribute("producto", producto);
		model.addAttribute("cantidad", cantidad);

		return "addCarrito";
	
	}
	
	
	@GetMapping("/carrito")
	public String carrito(HttpServletRequest request, Model model,@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "cantidad", required = false) Integer cantidad, @RequestParam(value = "modificar", required = false) String modificar, @RequestParam(value = "eliminar", required = false) String eliminar) {
		
		HashMap<Producto,Integer> carrito = carritoService.recuperarCarrito(request);
		carrito = carritoService.actualizarProducto(carrito, id, cantidad, modificar, eliminar);
		model.addAttribute("carrito", carrito);
		carritoService.contarCarrito(carrito, request);
		
		return "carrito";
	}
	
	@GetMapping("/comprar")
	public String comprar() {
		return "metodoPago";
	}
	
	@GetMapping("/resumenPedido")
	public String resumenPedido(HttpServletRequest request, Model model) {
		HashMap<Producto,Integer> carrito = carritoService.recuperarCarrito(request);
		model.addAttribute("carrito", carrito);
		return "resumenPedido";
	}
	
	
}
