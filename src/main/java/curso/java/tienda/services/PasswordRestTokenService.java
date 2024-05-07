package curso.java.tienda.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import curso.java.tienda.Entities.PasswordReset;

import curso.java.tienda.Repositories.PasswordResetTokenRepository;


@Service
public class PasswordRestTokenService {

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	
	public void guardar (PasswordReset token) {
		passwordResetTokenRepository.save(token);
		
	}
	
	public void borrar (PasswordReset token) {
		passwordResetTokenRepository.delete(token);
		
	}
	
	
	public PasswordReset validarToken(String token) {
        
        return passwordResetTokenRepository.findByTokenAndNotExpired(token);
    }


}
