package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

import com.example.demo.Config.Global;
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
		model.addAttribute("IMG", Global.URL);
		model.addAttribute("listaProductos", productoRepositorio.findAll());
		return "admin/productos";
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
			
			return "registrarProducto";
		}
		else {
			productoRepositorio.save(producto);  //Lo guardo para que se genere el ID
			
			if (!file.isEmpty()) {
				
				String imagen = storageService.store(file, producto.getId(), producto.getNombre());
				producto.setImagen(imagen);
				
			}
			producto.setFechaAlta(new java.sql.Timestamp(System.currentTimeMillis()));
			productoRepositorio.save(producto);			
			
			return "redirect:/productos";
		}
	}
	
	
	//EDICIONES
	@GetMapping("/gestionProducto")
	public String gestionProducto( Model model, @RequestParam("accion") String valor, @RequestParam("id") Long id) {
		model.addAttribute("IMG", Global.URL);
		model.addAttribute("categorias", CategoriaRepository.findAll());
		Producto producto = productoRepositorio.findById(id).orElse(null);
		
		if(valor.equals("editar")) return "redirect:/editarProducto/" + id;
		else if (valor.equals("eliminar")) {
			productoRepositorio.softDeleteById(id);
			return "redirect:/productos";
		}
		else if (valor.equals("activar")) {
			productoRepositorio.reactivateById(id);
			return "redirect:/productos";
		}
		
		model.addAttribute("productoForm", producto);
		return "registrarProducto";
	}
	
	
	@GetMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id, Model model) {
		model.addAttribute("categorias", CategoriaRepository.findAll());
		Producto producto = productoRepositorio.findById(id).orElse(null);
		

		model.addAttribute("productoForm", producto);
		return "registrarProducto";
	}
	

	@PostMapping("/editarProducto/submit")
	public String editarProductoSubmit(@Valid @ModelAttribute("productoForm") Producto producto, @RequestParam("file") MultipartFile file, BindingResult validacion, Model model) {
		model.addAttribute("IMG", Global.URL);
		Producto viejo = productoRepositorio.findById(producto.getId()).orElse(null);
		producto.setFechaAlta(viejo.getFechaAlta());
		producto.setImagen(viejo.getImagen());
		
		
		
		if(validacion.hasErrors()) {
			model.addAttribute("categorias", CategoriaRepository.findAll());
			return "registrarProducto";
		}
		else {
			
			if (!file.isEmpty()) {
				
				String imagen = storageService.store(file, producto.getId(), producto.getNombre());
				producto.setImagen(imagen);
				
			}
			
			productoRepositorio.save(producto);
			
			return "redirect:/productos";
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
		model.addAttribute("IMG", Global.URL);
		Producto producto = productoRepositorio.findById(id).orElse(null);
		model.addAttribute("categorias", CategoriaRepository.findAll());
		model.addAttribute("producto", producto);
		return "productoDetalle";
	}
	
	
	

}
