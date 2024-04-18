package curso.java.tienda.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.Entities.Metodos_pago;

public interface Metodos_pagoRespository extends JpaRepository<Metodos_pago, Long> {

}
