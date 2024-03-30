package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "valoraciones")
public class Valoracion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int id_producto;
	
	@Column
	private int id_usuario;
	
	@Column
	private int valoracion;
	
	@Column
	private String comentario;

	public Valoracion() {
		super();
	}

	public Valoracion(Long id, int id_producto, int id_usuario, int valoracion, String comentario) {
		super();
		this.id = id;
		this.id_producto = id_producto;
		this.id_usuario = id_usuario;
		this.valoracion = valoracion;
		this.comentario = comentario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return "valoraciones [id=" + id + ", id_producto=" + id_producto + ", id_usuario=" + id_usuario
				+ ", valoracion=" + valoracion + ", comentario=" + comentario + "]";
	}

	
	

}
