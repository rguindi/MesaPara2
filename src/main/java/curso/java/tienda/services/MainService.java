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



@Service
public class MainService {
	
	@Autowired
    private JavaMailSender mailSender;

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
	              System.out.println("Error al enviar el correo electrónico: " + e.getMessage());
	          }
	    }
		
	   
	   
}
