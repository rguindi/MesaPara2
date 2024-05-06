package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.java.tienda.Entities.PasswordReset;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordReset, Long> {

	
	 @Query("SELECT prt FROM PasswordReset prt WHERE prt.token = ?1 AND prt.fechaExpiracion > CURRENT_TIMESTAMP")
	    PasswordReset findByTokenAndNotExpired(String token);
}
