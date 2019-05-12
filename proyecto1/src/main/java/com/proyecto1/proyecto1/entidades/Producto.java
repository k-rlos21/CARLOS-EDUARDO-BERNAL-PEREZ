package com.proyecto1.proyecto1.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="producto")
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="accountid")
	private String accountId;
	
	@Column(name="startdate")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name="enddate")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name="limite")
	private float limite;
	
	@Column(name="available")
	private float available;
	
	@Column(name="total")
	private float total;
	
	@Column(name="debit")
	private float debit;
	
	@Column(name="interestrate")
	private float interestRate;
	
	@Column(name="interestamount")
	private float interestAmount;
	
	@Column(name="monthlycut")
	private Integer monthlyCut;
	
	@JsonIgnore
	@JoinColumn(name="idcliente", referencedColumnName="idcliente")
	@ManyToOne
	private Cliente cliente;
	
	@JsonIgnore
	@JoinColumn(name="idtipoproducto", referencedColumnName="idtipoproducto")
	@ManyToOne
	private TipoProducto tipoProducto;
	
	@JsonIgnore
	@JoinTable(name = "producto_transaccion", joinColumns = {
            @JoinColumn(name = "accountid", referencedColumnName = "accountid")}, inverseJoinColumns = {
            @JoinColumn(name = "idtransaccion", referencedColumnName = "idtransaccion")})
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("transactiondate DESC")
	private Set<Transaccion> transaccionesList;

	public Producto() {
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public float getLimite() {
		return limite;
	}

	public void setLimite(float limit) {
		this.limite = limit;
	}

	public float getAvailable() {
		return available;
	}

	public void setAvailable(float available) {
		this.available = available;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getDebit() {
		return debit;
	}

	public void setDebit(float debit) {
		this.debit = debit;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public float getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(float interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Integer getMonthlyCut() {
		return monthlyCut;
	}

	public void setMonthlyCut(Integer monthlyCut) {
		this.monthlyCut = monthlyCut;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public Set<Transaccion> getTransaccionesList() {
		return transaccionesList;
	}

	public void setTransaccionesList(Set<Transaccion> transaccionesList) {
		this.transaccionesList = transaccionesList;
	}

	@Override
	public String toString() {
		return "Producto [accountId=" + accountId + ", startDate=" + startDate + ", endDate=" + endDate + ", limite="
				+ limite + ", available=" + available + ", total=" + total + ", debit=" + debit + ", interestRate="
				+ interestRate + ", interestAmount=" + interestAmount + ", monthlyCut=" + monthlyCut + ", cliente="
				+ cliente + ", tipoProducto=" + tipoProducto + ", transaccionesList=" + transaccionesList + "]";
	}

}
