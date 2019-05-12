package com.proyecto1.proyecto1.utilitarias;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto1.proyecto1.entidades.Producto;
import com.proyecto1.proyecto1.entidades.Transaccion;
import com.proyecto1.proyecto1.repositorio.ProductoRepositorio;
import com.proyecto1.proyecto1.repositorio.TransaccionRepositorio;

@Component
public class PagoPrestamo {
	
	@Autowired
	ProductoRepositorio productoRepositorio;
	
	@Autowired
	TransaccionRepositorio transaccionRepositorio;
	
	@Autowired
	NumeroTransaccion numeroTransaccion;
	
	public PagoPrestamo(){
		
	}
	
	public String pagarPrestamo(Producto cuenta1, Producto cuenta2, float monto){
		
		String pagoExitoso = "";
		
		float saldoAnteriorCuenta1 = cuenta1.getAvailable();
		float debitoAnteriorCuenta2 = cuenta2.getDebit();

		Transaccion transaccion = new Transaccion();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String numeroTrans = numeroTransaccion.calcularSiguienteTransaccion();
		
		transaccion.setAmount(-1*monto);
		transaccion.setTransactionDate(timestamp);
		transaccion.setDescription("Pago de Prestamo Bancario");
		transaccion.setIdTransaccion(numeroTrans);
		
		cuenta1.setAvailable(saldoAnteriorCuenta1-monto);
		cuenta2.setDebit(debitoAnteriorCuenta2-monto);
		
		try{
			
			transaccionRepositorio.save(transaccion);
			
			cuenta1.getTransaccionesList().add(transaccion);
			cuenta2.getTransaccionesList().add(transaccion);
			
			productoRepositorio.save(cuenta1);
			productoRepositorio.save(cuenta2);
			
			pagoExitoso = transaccion.getIdTransaccion();
			
		}catch(Exception e){
			
			pagoExitoso = "";
		}
		
		return pagoExitoso;
		
	}

}
