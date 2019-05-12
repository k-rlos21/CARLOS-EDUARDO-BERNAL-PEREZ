package com.proyecto1.proyecto1.restController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.proyecto1.entidades.Beneficiario;
import com.proyecto1.proyecto1.entidades.Cliente;
import com.proyecto1.proyecto1.entidades.Producto;
import com.proyecto1.proyecto1.repositorio.BeneficiarioRepositorio;
import com.proyecto1.proyecto1.repositorio.ClienteRepositorio;
import com.proyecto1.proyecto1.repositorio.ProductoRepositorio;
import com.proyecto1.proyecto1.utilitarias.ValidarBeneficiario;

@RestController
@RequestMapping("/beneficiario")
public class BeneficiarioController {
	
	@Autowired
	BeneficiarioRepositorio beneficiarioRepositorio;
	
	@Autowired
	ClienteRepositorio clienteRepositorio;
	
	@Autowired
	ProductoRepositorio productoRepositorio;
	
	@Autowired
	ValidarBeneficiario validarBeneficiario;
	
	@PostMapping("afiliar")
	public ResponseEntity<String> afiliarBeneficiario(HttpServletRequest request){
		
		String numeroCuentaBeneficiario;
		String nombreBeneficiario;
		int tipoCuenta;
		String correoElectronico;
		Boolean afiliado = false;
		
		String idCliente = "CL001";
		
		Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
		
		numeroCuentaBeneficiario = request.getParameter("numeroCuentaBeneficiario");
		nombreBeneficiario = request.getParameter("nombreBeneficiario");
		tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
		correoElectronico = request.getParameter("correoElectronico");
		
		Producto cuentaBeneficiario = productoRepositorio.findById(numeroCuentaBeneficiario).orElse(null);
		
		if(cuentaBeneficiario==null){
			return new ResponseEntity<>("Cuenta No encontrada",HttpStatus.NOT_FOUND);
		}
		
		if(!cuentaBeneficiario.getTipoProducto().getIdTipoProducto().equals(tipoCuenta)){
			return new ResponseEntity<>("Cuenta No encontrada",HttpStatus.NOT_FOUND);
		}
		
		if(!correoElectronico.contains("@")){
			return new ResponseEntity<>("Correo Invalido",HttpStatus.BAD_REQUEST);
		}
		
		Beneficiario beneficiario = beneficiarioRepositorio.findByNombreBeneficiario(nombreBeneficiario);
		
		afiliado = validarBeneficiario.validarBeneficiario(cliente, beneficiario);
		
		if(afiliado){
			return new ResponseEntity<>("Beneficiario ya se encuentra afiliado al cliente",HttpStatus.CONFLICT);
		}
		
		if(beneficiario==null){
			beneficiario = new Beneficiario();
		}
		
		beneficiario.setNumeroCuenta(numeroCuentaBeneficiario);
		beneficiario.setNombreBeneficiario(nombreBeneficiario);
		beneficiario.setCorreoElectronico(correoElectronico);
		beneficiario.setCliente(cliente);
		beneficiario.setTipoCuentaBeneficiario(tipoCuenta);
		
		try{
			beneficiarioRepositorio.save(beneficiario);
			return new ResponseEntity<>("Afiliacion realizada de forma exitosa",HttpStatus.CREATED);
		}catch(Exception e){
			return new ResponseEntity<>("Error, contacte a soporte",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/{beneficiaryId}")
	public ResponseEntity<String> eliminarBeneficiario(@PathVariable int beneficiaryId){
		
		Boolean afiliado = false;
		
		String idCliente = "CL001";
		
		Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
		
		Beneficiario beneficiario = beneficiarioRepositorio.findById(beneficiaryId).orElse(null);
		
		if(beneficiario==null){
			return new ResponseEntity<>("Beneficiario No encontrado",HttpStatus.NOT_FOUND);
		}
		
		
		afiliado = validarBeneficiario.validarBeneficiario(cliente, beneficiario);
		
		if(!afiliado){
			return new ResponseEntity<>("Beneficiario Invalido",HttpStatus.NOT_FOUND);
		}
		
		try{
			System.out.println("entra en el try");
			beneficiarioRepositorio.delete(beneficiario);
			return new ResponseEntity<>("Beneficiario eliminado de forma exitosa",HttpStatus.NO_CONTENT);
			
		}catch(Exception e){
			return new ResponseEntity<>("Error, contacte a soporte",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
