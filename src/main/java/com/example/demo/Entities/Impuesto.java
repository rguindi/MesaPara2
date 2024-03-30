package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "impuesto")
public class Impuesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private float impuesto;

	public Impuesto() {
		super();
	}

	public Impuesto(Long id, float impuesto) {
		super();
		this.id = id;
		this.impuesto = impuesto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}

	@Override
	public String toString() {
		return "impuestos [id=" + id + ", impuesto=" + impuesto + "]";
	}

	
	
}
