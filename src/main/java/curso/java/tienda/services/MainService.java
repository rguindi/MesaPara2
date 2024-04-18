package curso.java.tienda.services;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

@Service
public class MainService {

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
}
