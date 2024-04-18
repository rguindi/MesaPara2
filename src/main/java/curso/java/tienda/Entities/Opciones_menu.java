package curso.java.tienda.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "opciones_menu")
public class Opciones_menu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int id_rol;
	
	@Column
	private String nombre_opcion;
	
	@Column
	private String url_opcion;

	public Opciones_menu() {
		super();
	}

	public Opciones_menu(Long id, int id_rol, String nombre_opcion, String url_opcion) {
		super();
		this.id = id;
		this.id_rol = id_rol;
		this.nombre_opcion = nombre_opcion;
		this.url_opcion = url_opcion;
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

	public String getNombre_opcion() {
		return nombre_opcion;
	}

	public void setNombre_opcion(String nombre_opcion) {
		this.nombre_opcion = nombre_opcion;
	}

	public String getUrl_opcion() {
		return url_opcion;
	}

	public void setUrl_opcion(String url_opcion) {
		this.url_opcion = url_opcion;
	}

	@Override
	public String toString() {
		return "opciones_menu [id=" + id + ", id_rol=" + id_rol + ", nombre_opcion=" + nombre_opcion + ", url_opcion="
				+ url_opcion + "]";
	}
	
	
	

}
