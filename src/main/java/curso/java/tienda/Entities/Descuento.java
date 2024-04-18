package curso.java.tienda.Entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "descuentos")
public class Descuento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String codigo;
	
	@Column
	private float descuento;
	
	@Column
	private Timestamp fecha_inicio;
	
	@Column
	private Timestamp fecha_fin;

	public Descuento() {
		super();
	}

	public Descuento(Long id, String codigo, float descuento, Timestamp fecha_inicio, Timestamp fecha_fin) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descuento = descuento;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public Timestamp getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Timestamp fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Timestamp getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Timestamp fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	@Override
	public String toString() {
		return "descuentos [id=" + id + ", codigo=" + codigo + ", descuento=" + descuento + ", fecha_inicio="
				+ fecha_inicio + ", fecha_fin=" + fecha_fin + "]";
	}
	
	
	
	
	
	

}
