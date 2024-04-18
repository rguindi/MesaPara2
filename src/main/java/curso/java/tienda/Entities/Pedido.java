package curso.java.tienda.Entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private Long id_usuario;
	
	@Column
	private Timestamp fecha;
	
	@Column
	private String metodo_pago;
	
	@Column
	private String estado;
	
	@Column
	private String num_factura;
	
	@Column
	private double total;

	public Pedido() {
	
	}

	public Pedido(Long id_usuario, Timestamp fecha, String metodo_pago, String estado, String num_factura,
			double total) {
		super();
		this.id_usuario = id_usuario;
		this.fecha = fecha;
		this.metodo_pago = metodo_pago;
		this.estado = estado;
		this.num_factura = num_factura;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNum_factura() {
		return num_factura;
	}

	public void setNum_factura(String num_factura) {
		this.num_factura = num_factura;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", id_usuario=" + id_usuario + ", fecha=" + fecha + ", metodo_pago=" + metodo_pago
				+ ", estado=" + estado + ", num_factura=" + num_factura + ", total=" + total + "]";
	}
	
	
	
	

}
