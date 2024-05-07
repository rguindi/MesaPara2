package curso.java.tienda.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Configuracion;
import curso.java.tienda.Entities.DetallePedido;
import curso.java.tienda.Entities.Pedido;
import curso.java.tienda.Entities.Producto;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.Repositories.ConfiguracionRespository;
import curso.java.tienda.Repositories.ProductoRepository;

@Service
public class PdfService {
	
	@Autowired
	ConfiguracionRespository configuracionRepository;
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	DetalleService detalleService;
	
	
	
	public  byte[] generarPDF(Usuario user, Pedido pedido) {
		Configuracion conf;
		conf = configuracionRepository.findFirstByClave("nombreTienda").orElse(null);
		String nombreTienda = conf.getValor();
		conf = configuracionRepository.findFirstByClave("cifTienda").orElse(null);
		String cifTienda =conf.getValor();
		conf = configuracionRepository.findFirstByClave("direccionTienda").orElse(null);
		String direccionTienda =conf.getValor();
		conf = configuracionRepository.findFirstByClave("cpTienda").orElse(null);
		String cpTienda =conf.getValor();
		conf = configuracionRepository.findFirstByClave("poblacionTienda").orElse(null);
		String poblacionTienda =conf.getValor();
		conf = configuracionRepository.findFirstByClave("tlfTienda").orElse(null);
		String tlfTienda =conf.getValor();
		
		List<DetallePedido> detalles = detalleService.porIdPedido(pedido.getId());
		
	
		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			PDDocument documento = new PDDocument();
			PDPage pagina = new PDPage (PDRectangle.A4);
			documento.addPage(pagina);
			PDPageContentStream contenido = new PDPageContentStream(documento, pagina);
			
			
			//VAMOS A CREAR LA CABECERA PARA TODAS LAS FACTURAS
			PDImageXObject logo = PDImageXObject.createFromFile("./pdf/MesaPara2.png", documento);
			contenido.drawImage(logo, 40, 690,  200, 100); //X Y W H
			
			contenido.beginText();
			PDFont fuenteNegrita = new PDType1Font(FontName.TIMES_BOLD);
			contenido.setFont(fuenteNegrita, 12);
			contenido.newLineAtOffset(340, 760);
			contenido.showText(nombreTienda);
			contenido.newLineAtOffset(0, -12);
			contenido.showText("CIF: " + cifTienda);
			contenido.newLineAtOffset(0, -12);
			contenido.showText(direccionTienda);
			contenido.newLineAtOffset(0, -12);
			contenido.showText("CP: " + cpTienda);
			contenido.newLineAtOffset(0, -12);
			contenido.showText(poblacionTienda);
			contenido.newLineAtOffset(0, -12);
			contenido.showText("Tlf: " + tlfTienda);
		
			//DATOS DE LA FACTURA
			contenido.newLineAtOffset(-210, -60);
			contenido.showText("RESUMEN DEL PEDIDO CON NÚMERO DE FACTURA " + pedido.getNum_factura());
			
			
			//DATOS DEL CLIENTE
			PDFont fuente = new PDType1Font(FontName.TIMES_ROMAN);
			contenido.setFont(fuente, 12);
			contenido.setNonStrokingColor(0.3f,0.3f,0.3f);
			contenido.newLineAtOffset(-80, -35);
			contenido.showText(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			contenido.newLineAtOffset(0, -12);
			contenido.showText("DNI: " + user.getDni());
			contenido.newLineAtOffset(0, -12);
			contenido.showText(user.getDireccion());
			contenido.newLineAtOffset(0, -12);
			contenido.showText("Provincia: " + user.getProvincia());
			contenido.newLineAtOffset(0, -12);
			contenido.showText("Localidad: " +user.getLocalidad());
			contenido.newLineAtOffset(0, -12);
			contenido.showText("Telefono: " +user.getTelefono());
			
			
			
			
			//LINEAS DE PRODUCTO
			contenido.setFont(fuenteNegrita, 12);
			contenido.setNonStrokingColor(0f,0f,0f);
			contenido.newLineAtOffset(0, -35);
			contenido.showText("Detalles del pedido");
			
			contenido.newLineAtOffset(0, -30);
			contenido.showText("");
			contenido.newLineAtOffset(125, 0);
			contenido.showText("Nombre");
			contenido.newLineAtOffset(110, 0);
			contenido.showText("Precio");
			contenido.newLineAtOffset(90, 0);
			contenido.showText("Unidades");
			contenido.newLineAtOffset(100, 0);
			contenido.showText("Total");
	
			int contador = 450;
			int contadorImagen = 435;
			for (DetallePedido detallePedido : detalles) {
				contenido.endText();
				//Imagen
				Producto producto = productoRepository.findById(detallePedido.getId_producto()).orElse(null);
				PDImageXObject imagen = PDImageXObject.createFromFile("./upload-dir/" + producto.getImagen(), documento);
				contenido.drawImage(imagen, 60, contadorImagen,  30, 30); 
				
				contenido.beginText();
				contenido.setFont(fuente, 12);
				contenido.setNonStrokingColor(0.3f,0.3f,0.3f);
				//Titulo
				contenido.newLineAtOffset(160, contador);
				contenido.showText(producto.getNombre());
				//Precio
				contenido.newLineAtOffset(130, 0);
				contenido.showText(String.valueOf(producto.getPrecio()) + " €");
				//Unidades
				contenido.newLineAtOffset(100, 0);
				contenido.showText(String.valueOf(detallePedido.getUnidades()));
				//Total
				contenido.newLineAtOffset(90, 0);
				contenido.showText(String.valueOf(detallePedido.getTotal())  + " €");
				
				
				contador-=40;
				contadorImagen-=40;
			}
	
			//Total
			contenido.newLineAtOffset(-60, -60);
			contenido.showText("Precio total: "+String.valueOf(pedido.getTotal())  + " €");
			contenido.endText();

			
			contenido.close();
			
		
			documento.save(byteArrayOutputStream);
			// Crear un ByteArrayOutputStream para almacenar el PDF generado
	       
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
		
	}

}
