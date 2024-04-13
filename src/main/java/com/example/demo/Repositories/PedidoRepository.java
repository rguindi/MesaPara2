package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
