package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "metodos_pago")
public class Metodos_pago {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String metodo_pago;

	public Metodos_pago() {
	
	}

	public Metodos_pago(Long id, String metodo_pago) {
		super();
		this.id = id;
		this.metodo_pago = metodo_pago;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	@Override
	public String toString() {
		return "metodos_pago [id=" + id + ", metodo_pago=" + metodo_pago + "]";
	}
	
	
	
	
	

}
