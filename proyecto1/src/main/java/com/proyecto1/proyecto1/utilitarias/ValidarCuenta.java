package com.proyecto1.proyecto1.utilitarias;

import org.springframework.stereotype.Component;

import com.proyecto1.proyecto1.entidades.Producto;

@Component
public class ValidarCuenta {
	
	public ValidarCuenta(){
		
	}
	
	public Boolean cuentaCliente(Producto cuenta, String idCliente){
		
		Boolean cuentaValida = false;
		
		if(cuenta.getCliente().getIdCliente().equals(idCliente)){
			cuentaValida = true;
		}else{
			cuentaValida = false;
		}
		
		return cuentaValida;
	}
	
	public Boolean cuentasCliente(Producto cuentaOrigen, Producto cuentaDestino, String idCliente){
		
		Boolean cuentasValidas = false;
		
		if(cuentaOrigen.getCliente().getIdCliente().equals(cuentaDestino.getCliente().getIdCliente())&&cuentaOrigen.getCliente().getIdCliente().equals(idCliente)){
			cuentasValidas = true;
		}else{
			cuentasValidas = false;
		}
		
		return cuentasValidas;
	}

}
