package curso.java.tienda.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.services.CategoriaService;
import curso.java.tienda.services.ProductoService;
import curso.java.tienda.upload.storage.StorageService;

import org.springframework.core.io.Resource;

import jakarta.validation.Valid;

@Controller
public class ProductoController {
	 @Autowired
	    private MessageSource variables;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping("/productos")
	public String listadoProductos(Model model) {
		model.addAttribute("pag", "producto");
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		model.addAttribute("listaProductos", productoService.todos());
		return "admin/productos";
	}
	
	
	//REGISTROS
	
	@GetMapping("/registrarProducto")
	public String registarProducto(Model model) {
		
		model.addAttribute("categorias", categoriaService.recuperarCategorias());
		
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
			productoService.guardar(producto); //Lo guardo para que se genere el ID
			
			if (!file.isEmpty()) {
				
				String imagen = storageService.store(file, producto.getId(), producto.getNombre());
				producto.setImagen(imagen);
				
			}
			producto.setFechaAlta(new java.sql.Timestamp(System.currentTimeMillis()));
			productoService.guardar(producto);			
			
			return "redirect:/productos";
		}
	}
	
	
	//EDICIONES
	@GetMapping("/gestionProducto")
	public String gestionProducto( Model model, @RequestParam("accion") String valor, @RequestParam("id") Long id) {
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		model.addAttribute("categorias", categoriaService.recuperarCategorias());
		Producto producto = productoService.recuperarProducto(id);

		if(valor.equals("editar")) return "redirect:/editarProducto/" + id;
		else if (valor.equals("eliminar")) {
			productoService.borradoLogico(id);
			return "redirect:/productos";
		}
		else if (valor.equals("activar")) {
			productoService.reactivar(id);
			return "redirect:/productos";
		}
		model.addAttribute("productoForm", producto);
		return "registrarProducto";
	}
	
	
	@GetMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id, Model model) {
		model.addAttribute("categorias", categoriaService.recuperarCategorias());
		Producto producto = productoService.recuperarProducto(id);
		model.addAttribute("productoForm", producto);
		return "registrarProducto";
	}
	

	@PostMapping("/editarProducto/submit")
	public String editarProductoSubmit(@Valid @ModelAttribute("productoForm") Producto producto, @RequestParam("file") MultipartFile file, BindingResult validacion, Model model) {
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		Producto viejo = productoService.recuperarProducto(producto.getId());  
		producto.setFechaAlta(viejo.getFechaAlta());
		producto.setImagen(viejo.getImagen());
		if(validacion.hasErrors()) {
			model.addAttribute("categorias", categoriaService.recuperarCategorias());
			return "registrarProducto";
		}
		else {
			if (!file.isEmpty()) {
				String imagen = storageService.store(file, producto.getId(), producto.getNombre());
				producto.setImagen(imagen);
				
			}
			productoService.guardar(producto);
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
		model.addAttribute("IMG", variables.getMessage("imagenes", null, LocaleContextHolder.getLocale()));
		Producto producto = productoService.recuperarProducto(id);
		model.addAttribute("categorias", categoriaService.recuperarCategorias());
		model.addAttribute("producto", producto);
		return "productoDetalle";
	}
	
	
	

}