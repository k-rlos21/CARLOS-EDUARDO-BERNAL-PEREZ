package com.proyecto1.proyecto1.utilitarias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto1.proyecto1.repositorio.TransaccionRepositorio;

@Component
public class NumeroTransaccion {
	
	@Autowired
	TransaccionRepositorio transaccionRepositorio;
	
	public NumeroTransaccion(){
		
	}
	
	public String calcularSiguienteTransaccion(){
		
		String siguienteTransaccion;
		int maximo;
		
		String maximaActual = transaccionRepositorio.maximaTransaccion();
		
		
		if(maximaActual.isEmpty()){
			siguienteTransaccion = "TRA001";
		}else{
			maximo = Integer.parseInt(maximaActual.replaceAll("TRA00", ""))+1;

			siguienteTransaccion = "TRA00"+maximo;
		}
		
		return siguienteTransaccion;
	}

}
