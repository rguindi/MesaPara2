package curso.java.tienda.services;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import curso.java.tienda.Repositories.ConfiguracionRespository;



@Service
public class MainService {
	
	@Autowired
    private JavaMailSender mailSender;
	

	@Autowired
    private ConfiguracionRespository configuracionRepository;
	
	@Autowired
	LoggingService log;

	   public Map<String, String> cargarPropertiesComoMapa(String nombreArchivo) {
	        Map<String, String> mapaProperties = new HashMap<>();
	        ResourceBundle bundle = ResourceBundle.getBundle(nombreArchivo);
	        Enumeration<String> keys = bundle.getKeys();
	        while (keys.hasMoreElements()) {
	            String key = keys.nextElement();
	            String value = bundle.getString(key);
	            mapaProperties.put(key, value);
	        }
	        return mapaProperties;
	    }
	   
	   public Model validar(String email, String nombre, String consulta, Model model) {
		   if(email.isBlank())  model.addAttribute("errorEmail", "El email no puede estar vacío");
		   if(nombre.isBlank())  model.addAttribute("errorNombre", "El nombre no puede estar vacío");
		   if(consulta.isBlank())  model.addAttribute("errorConsulta", "La consulta no puede estar vacía");
		
		   return model;
	   }
	   
	   
	   
	 //Pasamos por parametro: destinatario, asunto y el mensaje
	    public void sendEmail(String to, String subject, String content) {

	    	  try {
	              SimpleMailMessage email = new SimpleMailMessage();
	              email.setTo(to);
	              email.setSubject(subject);
	              email.setText(content);
	              mailSender.send(email);
	              System.out.println("Email enviado con éxito a: " + to);
	          } catch (Exception e) {
	        	  log.logError("Error al enviar el correo electrónico");
	              System.out.println("Error al enviar el correo electrónico: " + e.getMessage());
	          }
	    }
		
	    //Devluelve en un mapa los datos de la empresa
	    public Map<String, String> datosEmpresa() {
	        Map<String, String> datosEmpresa= new HashMap<>();
	        
	       String nombre = configuracionRepository.findFirstByClave("nombreTienda").orElse(null).getValor();
	       String cif = configuracionRepository.findFirstByClave("cifTienda").orElse(null).getValor();
	       String direccion = configuracionRepository.findFirstByClave("direccionTienda").orElse(null).getValor();
	       String cp = configuracionRepository.findFirstByClave("cpTienda").orElse(null).getValor();
	       String poblacion = configuracionRepository.findFirstByClave("poblacionTienda").orElse(null).getValor();
	       String tlf = configuracionRepository.findFirstByClave("tlfTienda").orElse(null).getValor();
	        
	            datosEmpresa.put("nombre", nombre);
	            datosEmpresa.put("cif", cif);
	            datosEmpresa.put("direccion", direccion);
	            datosEmpresa.put("cp", cp);
	            datosEmpresa.put("poblacion", poblacion);
	            datosEmpresa.put("tlf", tlf);
	        
	        return datosEmpresa;
	    }
	    
	   
	   
}
