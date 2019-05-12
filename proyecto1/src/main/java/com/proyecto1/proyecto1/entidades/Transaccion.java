package com.proyecto1.proyecto1.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="transaccion")
public class Transaccion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idtransaccion")
	private String idTransaccion;
	
	@Column(name="transactiondate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@Column(name="description")
	private String description;
	
	@Column(name="amount")
	private float amount;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "transaccionesList")
	private List<Producto> productosList;

	public Transaccion() {
	}

	public String getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public List<Producto> getProductosList() {
		return productosList;
	}

	public void setProductosList(List<Producto> productosList) {
		this.productosList = productosList;
	}

	@Override
	public String toString() {
		return "Transaccion [idTransaccion=" + idTransaccion + ", transactionDate=" + transactionDate + ", description="
				+ description + ", amount=" + amount + ", productosList=" + productosList + "]";
	}

}
