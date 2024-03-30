package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Descuento;

public interface DescuentosRepository extends JpaRepository<Descuento, Long> {

}
