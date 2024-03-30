package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "detalles_pedido")
public class Detalles_pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int id_pedido;
	
	@Column
	private int id_producto;
	
	@Column
	private float precio_unidad;
	
	@Column
	private int unidades;
	
	@Column
	private float impuesto;
	
	@Column
	private double total;

	public Detalles_pedido() {
		super();
	}
	
	

	public Detalles_pedido(Long id, int id_pedido, int id_producto, float precio_unidad, int unidades, float impuesto,
			double total) {
		super();
		this.id = id;
		this.id_pedido = id_pedido;
		this.id_producto = id_producto;
		this.precio_unidad = precio_unidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = total;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public float getPrecio_unidad() {
		return precio_unidad;
	}

	public void setPrecio_unidad(float precio_unidad) {
		this.precio_unidad = precio_unidad;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}



	@Override
	public String toString() {
		return "detalles_pedido [id=" + id + ", id_pedido=" + id_pedido + ", id_producto=" + id_producto
				+ ", precio_unidad=" + precio_unidad + ", unidades=" + unidades + ", impuesto=" + impuesto + ", total="
				+ total + "]";
	}
	
	
	
	
	


}
