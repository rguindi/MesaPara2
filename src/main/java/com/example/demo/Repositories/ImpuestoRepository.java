package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Impuesto;

public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

}
