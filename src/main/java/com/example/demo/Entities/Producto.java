package com.example.demo.Entities;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "productos")
public class Producto {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "id_categoria")
	private int id_categoria;
	
	@NotEmpty
	@Column(name = "nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;

	@NotEmpty
	@Column(name = "precio")
	private double precio;
	
	@NotEmpty
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "fecha_alta")
	private Timestamp fecha_alta;
	
	@Column(name = "fecha_baja")
	private Timestamp fecha_baja;
	
	@Column(name = "impuesto")
	private float impuesto;

	@Column(name = "imagen")
	private String imagen;

	
	
	public Producto() {

	}



	public Producto(long id, int id_categoria, String nombre, String descripcion, double precio, int stock,
			Timestamp fecha_alta, Timestamp fecha_baja, float impuesto, String imagen) {
		super();
		this.id = id;
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.fecha_alta = fecha_alta;
		this.fecha_baja = fecha_baja;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public int getId_categoria() {
		return id_categoria;
	}



	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public double getPrecio() {
		return precio;
	}



	public void setPrecio(double precio) {
		this.precio = precio;
	}



	public int getStock() {
		return stock;
	}



	public void setStock(int stock) {
		this.stock = stock;
	}



	public Timestamp getFecha_alta() {
		return fecha_alta;
	}



	public void setFecha_alta(Timestamp fecha_alta) {
		this.fecha_alta = fecha_alta;
	}



	public Timestamp getFecha_baja() {
		return fecha_baja;
	}



	public void setFecha_baja(Timestamp fecha_baja) {
		this.fecha_baja = fecha_baja;
	}



	public float getImpuesto() {
		return impuesto;
	}



	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}



	public String getImagen() {
		return imagen;
	}



	public void setImagen(String imagen) {
		this.imagen = imagen;
	}



	@Override
	public String toString() {
		return "Producto [id=" + id + ", id_categoria=" + id_categoria + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", precio=" + precio + ", stock=" + stock + ", fecha_alta=" + fecha_alta
				+ ", fecha_baja=" + fecha_baja + ", impuesto=" + impuesto + ", imagen=" + imagen + "]";
	}
	
	
	
	


}
