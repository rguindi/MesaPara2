package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Producto;


public interface ProductoRepository extends JpaRepository<Producto, Long>  {
	
	List<Producto> findTop4ByOrderByFechaAltaDesc();
	
}
