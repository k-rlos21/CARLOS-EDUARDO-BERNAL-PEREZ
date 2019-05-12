package com.proyecto1.proyecto1.restController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.proyecto1.entidades.Producto;
import com.proyecto1.proyecto1.entidades.Transaccion;
import com.proyecto1.proyecto1.repositorio.ProductoRepositorio;
import com.proyecto1.proyecto1.repositorio.TransaccionRepositorio;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	ProductoRepositorio productoRepositorio;
	
	@Autowired
	TransaccionRepositorio transaccionRepositorio;
	
	@GetMapping("transacciones/{accountId}/{start}/{end}")
	public ResponseEntity<List<Transaccion>> transaccionesProducto(@PathVariable String accountId,
			@PathVariable String start,@PathVariable String end){
		
		String idCliente = "CL001";
		Date fecha1,fecha2;
		
		try{
			fecha1 = new SimpleDateFormat("dd-MM-yyyy").parse(start);
			fecha2 = new SimpleDateFormat("dd-MM-yyyy").parse(end);
		}catch(Exception e){
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
		Producto producto = productoRepositorio.findById(accountId).orElse(null);
		

		if(producto==null){
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		
		if(!producto.getCliente().getIdCliente().equals(idCliente)){//if separado porque podría darme error si lo coloco en el primero
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		
		List<Transaccion> transacciones = null;

		try{
			//transacciones = transaccionRepositorio.findByProductoAndTransactionDateBetween(producto, fecha1, fecha2);
			transacciones = transaccionRepositorio.findByTransactionDateBetweenAndProductosList_AccountId(fecha1, fecha2, accountId);
		}catch(Exception e){
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}	
		
		return new ResponseEntity<>(transacciones,HttpStatus.OK);
		
	}

}
