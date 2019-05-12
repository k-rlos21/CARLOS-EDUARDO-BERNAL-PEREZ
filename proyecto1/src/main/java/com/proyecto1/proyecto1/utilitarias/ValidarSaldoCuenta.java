package com.proyecto1.proyecto1.utilitarias;

import org.springframework.stereotype.Component;

import com.proyecto1.proyecto1.entidades.Producto;

@Component
public class ValidarSaldoCuenta {
	
	public ValidarSaldoCuenta(){
		
	}
	
	public Boolean saldoSuficiente(Producto cuenta, float monto){
		
		Boolean suficiente = false;
		
		if(cuenta.getAvailable()>= monto){
			suficiente = true;
		}
		else{
			suficiente = false;
		}
		
		return suficiente;
		
	}

}
