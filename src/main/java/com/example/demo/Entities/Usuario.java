package com.example.demo.Entities;





import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "id_rol")
	private int id_rol;
	
	@Column(name = "email")
	@Email (message = "{usuario.email.formato}")
	@NotEmpty
	private String email;
	
	@Column(name = "clave")
	@NotEmpty
	private String clave;
	
	@NotEmpty
	@Column(name = "nombre")
	private String nombre;
	
	
	@Column(name = "apellido1")
	@NotEmpty
	private String apellido1;
	
	
	@Column(name = "apellido2")
	private String apellido2;
	
	
	@Column(name = "direccion")
	@NotEmpty
	private String direccion;
	
	@Column(name = "provincia")
	@NotEmpty
	private String provincia;
	
	@Column(name = "localidad")
	@NotEmpty
	private String localidad;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "dni")
	@NotEmpty
	private String dni;
	
	@Column(name = "fecha_baja")
	private Timestamp fechaBaja;
	
	/*
	public Usuario() {

	}
	
	public Usuario(String email, String clave, String nombre, String apellido1, String apellido2,
			String direccion, String provincia, String localidad, String telefono, String dni) {
		
		this.id_rol = 2;
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.provincia = provincia;
		this.localidad = localidad;
		this.telefono = telefono;
		this.dni = dni;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	
	public Timestamp getFechaBaja() {
		return fechaBaja;
	}


	public void setFechaBaja(Timestamp fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


	public void setId(long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", id_rol=" + id_rol + ", email=" + email + ", clave=" + clave + ", nombre="
				+ nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", direccion=" + direccion
				+ ", provincia=" + provincia + ", localidad=" + localidad + ", telefono=" + telefono + ", dni=" + dni
				+ "]";
	}
	
	
	*/
	


}
