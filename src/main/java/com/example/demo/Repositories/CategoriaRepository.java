package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
