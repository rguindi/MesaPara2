package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.CategoriaRepository;
import com.example.demo.Repositories.ProductoRepository;
import com.example.demo.upload.storage.StorageService;
import org.springframework.core.io.Resource;

import jakarta.validation.Valid;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoRepository productoRepositorio;
	
	@Autowired
	private CategoriaRepository CategoriaRepository;
	
	@Autowired
	private StorageService storageService;
	
	@GetMapping("/productos")
	public String listadoProductos(Model model) {
		
		model.addAttribute("listaProductos", productoRepositorio.findAll());
		return "productos";
	}
	
	
	//REGISTROS
	
	@GetMapping("/registrarProducto")
	public String registarProducto(Model model) {
		
		model.addAttribute("categorias", CategoriaRepository.findAll());
		
		model.addAttribute("productoForm", new Producto());
		return "registrarProducto";
	}
	
	@PostMapping("/registrarProducto/submit")
	public String registrarProductoSubmit(@Valid @ModelAttribute("productoForm") Producto producto, 
			BindingResult validacion, @RequestParam("file") MultipartFile file, Model model) {
		
		if(validacion.hasErrors()) {
			model.addAttribute("categorias", CategoriaRepository.findAll());
			return "registrarProducto";
		}
		else {
			productoRepositorio.save(producto);  //Lo guardo para que se genere el ID
			
			if (!file.isEmpty()) {
				
				String imagen = storageService.store(file, producto.getId());
				producto.setImagen(MvcUriComponentsBuilder.fromMethodName(ProductoController.class, "serveFile", imagen).build().toUriString());
				
			}
			
			producto.setFechaAlta(new java.sql.Timestamp(System.currentTimeMillis()));
			productoRepositorio.save(producto);			
			return "redirect:/productos";
		}
	}
	
	
	//EDICIONES
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
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().body(file);
	}
	
	
	//DETALLE PRODUCTO
	@GetMapping("/producto/{id}")
	public String detalleProducto(@PathVariable Long id, Model model) {
	
		Producto producto = productoRepositorio.findById(id).orElse(null);
		
		model.addAttribute("producto", producto);
		return "productoDetalle";
	}
	
	
	

}
