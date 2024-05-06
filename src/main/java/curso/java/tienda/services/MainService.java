package curso.java.tienda.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import curso.java.tienda.Entities.PasswordReset;
import curso.java.tienda.Entities.Usuario;
import curso.java.tienda.Repositories.ConfiguracionRespository;
import jakarta.servlet.http.HttpServletRequest;



@Service
public class MainService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
    private PasswordRestTokenService passwordRestTokenService;
	
	

	@Autowired
    private ConfiguracionRespository configuracionRepository;
	
	@Autowired
	LoggingService log;
	
	@Autowired
	UsuarioService usuarioService;

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
	    
	    
	    public void comprobarAdmin() {
	    	Usuario admin = usuarioService.buscarPorEmail("superAdmin@superAdmin.com");
	    	if(admin == null) {
	    		System.out.println("No se ha encontrado el super Administrador. Creando usuario...");
	    		admin = new Usuario(0, 4, "superAdmin@superAdmin.com", "nxS7eFjM8iE5cLtBgITfzVz12g2fyJk5dUiuXY/EYLjfQpS58Qi6V3ju4JSWAsUf", "Nombre", "Apellido1", "Apellido2", "Direccion", "Provincia", "Municipio", "000000000", "00000000A", null);
	    		usuarioService.guardar(admin);
	    	}
	    }
	    
	    
	    public void crearTokenYEnviarEmail(Usuario usuario) {
	        String token = UUID.randomUUID().toString();
	        Timestamp fechaExpiracion = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15));

	        PasswordReset passwordResetToken = new PasswordReset();
	        passwordResetToken.setUsuario(usuario);
	        passwordResetToken.setToken(token);
	        passwordResetToken.setFechaExpiracion(fechaExpiracion);
	        passwordRestTokenService.guardar(passwordResetToken);
	        // Construir el mensaje con la URL completa incluyendo el token
	        String urlBase = obtenerURLBase();
	        String mensaje = urlBase + "/nuevoPass?token=" + token;
	        System.out.println("Mensaje a enviar por correo: " + mensaje);
	        this.sendEmail(usuario.getEmail(), "Recuperación de contraseña", mensaje);

	       

	    }
	    
	    private String obtenerURLBase() {
	        // Obtener la URL base de la aplicación
	        String urlBase = "";
	        try {
	            // Para obtener la URL base en un entorno de aplicación web
	            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	            urlBase = request.getRequestURL().toString();
	            String contexto = request.getContextPath();
	            urlBase = urlBase.replace(contexto, "");
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Manejar la excepción si no se puede obtener la URL base
	        }
	        return urlBase;
	    }
	   
	   
}
