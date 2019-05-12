package com.proyecto1.proyecto1.utilitarias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto1.proyecto1.entidades.Beneficiario;
import com.proyecto1.proyecto1.entidades.Cliente;
import com.proyecto1.proyecto1.repositorio.BeneficiarioRepositorio;
import com.proyecto1.proyecto1.repositorio.ClienteRepositorio;

@Component
public class ValidarBeneficiario {
	
	@Autowired
	BeneficiarioRepositorio beneficiarioRepositorio;
	
	@Autowired
	ClienteRepositorio clienteRepositorio;
	
	public Boolean validarBeneficiario(Cliente cliente, Beneficiario beneficiario){
		
		Boolean afiliado = false;
		
		if(beneficiario!=null){
			
			if(cliente.getBeneficiariosList().contains(beneficiario)){
				afiliado = true;
			}else{
				afiliado = false;
			}
			
		}
		
		return afiliado;
	}

}
