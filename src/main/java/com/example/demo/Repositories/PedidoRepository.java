package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entities.Pedido;

import jakarta.transaction.Transactional;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	 @Query("SELECT p FROM Pedido p WHERE p.id_usuario = :idUsuario")
	    List<Pedido> buscarPorIdUsuario(Long idUsuario);
	 
	 @Query("UPDATE Pedido p SET p.estado = 'PC' WHERE p.id = :idPedido")
	    @Modifying
	    @Transactional
	    void cambiarEstadoAPC(Long idPedido);

}
