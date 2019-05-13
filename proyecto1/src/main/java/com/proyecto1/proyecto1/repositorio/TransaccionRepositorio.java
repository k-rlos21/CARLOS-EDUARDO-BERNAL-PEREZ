package com.proyecto1.proyecto1.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto1.proyecto1.entidades.Transaccion;

public interface TransaccionRepositorio extends CrudRepository<Transaccion,String>{
	
	List<Transaccion> findByTransactionDateBetweenAndProductosList_AccountId(Date start,Date end, String accountId);
	
	@Query("select max(to_number(replace(t.idTransaccion,'TRA00',''),'99G999D9S')) from Transaccion t")
	String maximaTransaccion();

}
