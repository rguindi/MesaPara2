package com.example.demo.Entities;


import java.sql.Timestamp;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;

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
	private Long id_categoria;
	
	@NotEmpty
	@Length(max = 254, message = "{producto.nombre.longitud}")
	@Column(name = "nombre")
	private String nombre;
	

	@Column(name = "descripcion")
	@Length(max = 254, message = "{producto.nombre.longitud}")
	private String descripcion;


	@Column(name = "precio")
	private double precio;
	
	
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "fecha_alta")
	private Timestamp fechaAlta;
	
	@Column(name = "fecha_baja")
	private Timestamp fechaBaja;
	
	@Column(name = "impuesto")
	private float impuesto;

	@Column(name = "imagen")
	@Length(max = 254, message = "{producto.imagen.longitud}")
	private String imagen;

	
	
	public Producto() {

	}



	public Producto(long id, Long id_categoria, String nombre, String descripcion, double precio, int stock,
			Timestamp fecha_alta, Timestamp fechaBaja, float impuesto, String imagen) {
		super();
		this.id = id;
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.fechaAlta = fecha_alta;
		this.fechaBaja = fechaBaja;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public Long getId_categoria() {
		return id_categoria;
	}



	public void setId_categoria(Long id_categoria) {
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



	public Timestamp getFechaAlta() {
		return fechaAlta;
	}



	public void setFechaAlta(Timestamp fechaAlta) {
		this.fechaAlta = fechaAlta;
	}



	public Timestamp getFechaBaja() {
		return fechaBaja;
	}



	public void setFechaBaja(Timestamp fechaBaja) {
		this.fechaBaja = fechaBaja;
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
				+ descripcion + ", precio=" + precio + ", stock=" + stock + ", fechaAlta=" + fechaAlta + ", fechaBaja="
				+ fechaBaja + ", impuesto=" + impuesto + ", imagen=" + imagen + "]";
	}

	
	
	//Metodos para comprar los objetos en el carrito

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Producto producto = (Producto) obj;
	    return Objects.equals(id, producto.id);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}




}
