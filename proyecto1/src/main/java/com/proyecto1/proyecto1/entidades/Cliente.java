package com.proyecto1.proyecto1.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cliente")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idcliente")
	private String idCliente;
	
	@Column(name = "nombrescliente")
	private String nombresCliente;
	
	@Column(name="apellidoscliente")
	private String apellidosCliente;
	
	@JsonIgnore
	@JoinColumn(name="idusuario")
	@OneToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "cliente")
	@OrderBy("idtipoproducto ASC")
	private List<Producto> productosList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
	private List<Beneficiario> beneficiariosList;

	public Cliente() {
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombresCliente() {
		return nombresCliente;
	}

	public void setNombresCliente(String nombresCliente) {
		this.nombresCliente = nombresCliente;
	}

	public String getApellidosCliente() {
		return apellidosCliente;
	}

	public void setApellidosCliente(String apellidosCliente) {
		this.apellidosCliente = apellidosCliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Producto> getProductosList() {
		return productosList;
	}

	public void setProductosList(List<Producto> productosList) {
		this.productosList = productosList;
	}

	public List<Beneficiario> getBeneficiariosList() {
		return beneficiariosList;
	}

	public void setBeneficiariosList(List<Beneficiario> beneficiariosList) {
		this.beneficiariosList = beneficiariosList;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nombresCliente=" + nombresCliente + ", apellidosCliente="
				+ apellidosCliente + ", usuario=" + usuario + "]";
	}

}
