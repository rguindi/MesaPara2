package curso.java.tienda.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class AutoUpdateOrdersService {
	
	@Autowired
	PedidoService pedidoService;

    Thread taskThread;
    volatile boolean running = true;


    public void startTask(int intervalInSeconds, HttpSession miSesion) {
    	miSesion.setAttribute("autoUpdate", intervalInSeconds);
    	 running = true; 
    	
        taskThread = new Thread(() -> {
            while (running) {
            	System.out.println("Productos enviados cada " + intervalInSeconds + " segundos");
                try {
                	  Thread.sleep(intervalInSeconds * 1000);
                    
                    pedidoService.todosEnviados();           
                  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        taskThread.start(); // Iniciar el hilo de la tarea
    }

    public void stopTask(HttpSession miSesion) {
        running = false; 
        System.out.println("Tarea detenida");
//        // Interrumpir el hilo si está en espera (p. ej., dentro de Thread.sleep())
//        if (taskThread != null && taskThread.isAlive()) {
//            taskThread.interrupt();
//        }
        
        miSesion.removeAttribute("autoUpdate");
    }
}