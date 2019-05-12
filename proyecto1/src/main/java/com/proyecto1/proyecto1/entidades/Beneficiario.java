package com.proyecto1.proyecto1.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="beneficiario")
public class Beneficiario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idbeneficiario")
	private Integer idBeneficiario;
	
	@Column(name="numerocuenta")
	private String numeroCuenta;
	
	@Column(name="nombrebeneficiario")
	private String nombreBeneficiario;
	
	@Column(name="tipocuentabeneficiario")
	private Integer tipoCuentaBeneficiario;
	
	@Column(name="correoelectronico")
	private String correoElectronico;
	
	@JsonIgnore
	@JoinColumn(name="idcliente", referencedColumnName="idcliente")
	@ManyToOne
	private Cliente cliente;
	
	public Beneficiario(){
		
	}

	public Integer getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(Integer idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public Integer getTipoCuentaBeneficiario() {
		return tipoCuentaBeneficiario;
	}

	public void setTipoCuentaBeneficiario(Integer tipoCuentaBeneficiario) {
		this.tipoCuentaBeneficiario = tipoCuentaBeneficiario;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Beneficiario [idBeneficiario=" + idBeneficiario + ", numeroCuenta=" + numeroCuenta
				+ ", nombreBeneficiario=" + nombreBeneficiario + ", tipoCuentaBeneficiario=" + tipoCuentaBeneficiario
				+ ", correoElectronico=" + correoElectronico + ", cliente=" + cliente + "]";
	}

}
