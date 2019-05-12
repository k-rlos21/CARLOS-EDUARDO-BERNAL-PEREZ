package com.proyecto1.proyecto1.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.proyecto1.proyecto1.entidades.Beneficiario;

public interface BeneficiarioRepositorio extends CrudRepository<Beneficiario, Integer>{
	
	Beneficiario findByNombreBeneficiario(String nombreBeneficiario);

}
