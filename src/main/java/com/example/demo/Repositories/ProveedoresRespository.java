package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Entities.Valoracion;

public interface ProveedoresRespository extends JpaRepository<Valoracion, Long> {

}
