package com.proyecto1.proyecto1.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.proyecto1.entidades.Cliente;
import com.proyecto1.proyecto1.entidades.Producto;
import com.proyecto1.proyecto1.repositorio.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	ClienteRepositorio clienteRepositorio;
	
	@GetMapping("productos")
	public ResponseEntity<List<Producto>> productosCliente(){
		
		String idCliente = "CL001";

		List<Producto> productosCliente = new ArrayList<>();
		
		Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
		
		try{
			productosCliente = cliente.getProductosList();
		}catch(Exception e){
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}	
		
		return new ResponseEntity<>(productosCliente,HttpStatus.OK);
	}

}
