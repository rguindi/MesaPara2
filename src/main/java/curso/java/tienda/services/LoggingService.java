package curso.java.tienda.services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    private static final Logger errorLogger = LogManager.getLogger("ERROR_FILE");
    private static final Logger infoLogger = LogManager.getLogger("INFO_FILE");

    public void logError(String message) {
        errorLogger.error(message);
    }

    public void logInfo(String message) {
        infoLogger.info(message);
    }
}
