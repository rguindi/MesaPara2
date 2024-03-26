package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
