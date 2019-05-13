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
import com.proyecto1.proyecto1.utilitarias.PagoPrestamo;
import com.proyecto1.proyecto1.utilitarias.PagoTarjetaCredito;
import com.proyecto1.proyecto1.utilitarias.TransferirEfectivo;
import com.proyecto1.proyecto1.utilitarias.ValidarBeneficiario;
import com.proyecto1.proyecto1.utilitarias.ValidarCuenta;
import com.proyecto1.proyecto1.utilitarias.ValidarSaldoCuenta;

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
	
	@Autowired
	ValidarCuenta validarCuenta;
	
	@Autowired
	ValidarSaldoCuenta validarSaldoCuenta;
	
	@Autowired
	TransferirEfectivo transferirEfectivo;
	
	@Autowired
	PagoTarjetaCredito pagoTarjetaCredito;
	
	@Autowired
	PagoPrestamo pagoPrestamo;
	
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
	
	@PostMapping("transferenciaEfectivo")
	public ResponseEntity<String> transferenciaEfectivo(HttpServletRequest request){
		
		Producto cuentaOrigen;
		Producto cuentaDestino;
		int idBeneficiario;
		float montoTransferir;
		Boolean cuentaValida =false;
		Boolean montoSuficiente = false;
		String transferenciaExitosa = "";
		Boolean afiliado = false;
		
		String idCliente = "CL001";
		
		Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
		
		cuentaOrigen = productoRepositorio.findById(request.getParameter("idCuentaOrigen")).orElse(null);
		idBeneficiario = Integer.parseInt(request.getParameter("idBeneficiario"));
		montoTransferir = Float.parseFloat(request.getParameter("monto"));
		
		Beneficiario beneficiario = beneficiarioRepositorio.findById(idBeneficiario).orElse(null);
		
		if(beneficiario==null){
			return new ResponseEntity<>("Beneficiario No encontrado",HttpStatus.NOT_FOUND);
		}
		
		cuentaValida = validarCuenta.cuentaCliente(cuentaOrigen, idCliente);
		
		if(!(cuentaValida&&cuentaOrigen.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria"))){
			
			return new ResponseEntity<>("La cuenta No es válida",HttpStatus.NOT_FOUND);
		}
		
		afiliado = validarBeneficiario.validarBeneficiario(cliente, beneficiario);
		
		if(!afiliado){
			return new ResponseEntity<>("Beneficiario Invalido",HttpStatus.NOT_FOUND);
		}
		
		cuentaDestino = productoRepositorio.findById(beneficiario.getNumeroCuenta()).orElse(null);
		
		if(!cuentaDestino.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria")){
			return new ResponseEntity<>("Cuenta del beneficiario No es de tipo Bancaria",HttpStatus.BAD_REQUEST);
		}
		
		
		montoSuficiente = validarSaldoCuenta.saldoSuficiente(cuentaOrigen, montoTransferir);
		
		if(!montoSuficiente){
			return new ResponseEntity<>("El saldo de la cuenta No es suficiente",HttpStatus.CONFLICT);
		}
		
		transferenciaExitosa = transferirEfectivo.transferir(cuentaOrigen, cuentaDestino, montoTransferir);
		
		if(transferenciaExitosa.length()<1){
			return new ResponseEntity<>("Ocurrió un error en la transferencia",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<>("Transferencia No. "+transferenciaExitosa+" realizada de forma exitosa",HttpStatus.OK);
	}
	
	
	@PostMapping("pagoTarjetaCredito")
	public ResponseEntity<String> pagoTarjetaCredito(HttpServletRequest request){
		
		Producto cuentaOrigen;
		Producto cuentaDestino;
		int idBeneficiario;
		float montoTransferir;
		Boolean cuentaValida =false;
		Boolean montoSuficiente = false;
		String pagoExitoso = "";
		Boolean afiliado = false;
		
		String idCliente = "CL001";
		
		Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
		
		cuentaOrigen = productoRepositorio.findById(request.getParameter("idCuentaOrigen")).orElse(null);
		idBeneficiario = Integer.parseInt(request.getParameter("idBeneficiario"));
		montoTransferir = Float.parseFloat(request.getParameter("monto"));
		
		Beneficiario beneficiario = beneficiarioRepositorio.findById(idBeneficiario).orElse(null);
		
		if(beneficiario==null){
			return new ResponseEntity<>("Beneficiario No encontrado",HttpStatus.NOT_FOUND);
		}
		
		cuentaValida = validarCuenta.cuentaCliente(cuentaOrigen, idCliente);
		
		if(!(cuentaValida&&cuentaOrigen.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria"))){
			
			return new ResponseEntity<>("La cuenta No es válida",HttpStatus.NOT_FOUND);
		}
		
		afiliado = validarBeneficiario.validarBeneficiario(cliente, beneficiario);
		
		if(!afiliado){
			return new ResponseEntity<>("Beneficiario Invalido",HttpStatus.NOT_FOUND);
		}
		
		cuentaDestino = productoRepositorio.findById(beneficiario.getNumeroCuenta()).orElse(null);
		
		if(!cuentaDestino.getTipoProducto().getNombreTipoProducto().equals("Tarjeta de Crédito")){
			return new ResponseEntity<>("Cuenta del beneficiario No es de tipo Crédito",HttpStatus.BAD_REQUEST);
		}
		
		montoSuficiente = validarSaldoCuenta.saldoSuficiente(cuentaOrigen, montoTransferir);
		
		if(!montoSuficiente){
			return new ResponseEntity<>("El saldo de la cuenta No es suficiente",HttpStatus.CONFLICT);
		}
		
		pagoExitoso = pagoTarjetaCredito.pagarTarjeta(cuentaOrigen, cuentaDestino, montoTransferir);
		
		if(pagoExitoso.length()<1){
			return new ResponseEntity<>("Ocurrió un error en la transferencia",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Transferencia No. "+pagoExitoso+" realizada de forma exitosa",HttpStatus.OK);
	}
	
	@PostMapping("pagoPrestamo")
	public ResponseEntity<String> pagoPrestamo(HttpServletRequest request){
		
		Producto cuentaOrigen;
		Producto cuentaDestino;
		int idBeneficiario;
		float montoTransferir;
		Boolean cuentaValida =false;
		Boolean montoSuficiente = false;
		String pagoExitoso = "";
		Boolean afiliado = false;
		
		String idCliente = "CL001";
		
		Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
		
		cuentaOrigen = productoRepositorio.findById(request.getParameter("idCuentaOrigen")).orElse(null);
		idBeneficiario = Integer.parseInt(request.getParameter("idBeneficiario"));
		montoTransferir = Float.parseFloat(request.getParameter("monto"));
		
		Beneficiario beneficiario = beneficiarioRepositorio.findById(idBeneficiario).orElse(null);
		
		if(beneficiario==null){
			return new ResponseEntity<>("Beneficiario No encontrado",HttpStatus.NOT_FOUND);
		}
		
		cuentaValida = validarCuenta.cuentaCliente(cuentaOrigen, idCliente);
		
		if(!(cuentaValida&&cuentaOrigen.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria"))){
			
			return new ResponseEntity<>("La cuenta No es válida",HttpStatus.NOT_FOUND);
		}
		
		afiliado = validarBeneficiario.validarBeneficiario(cliente, beneficiario);
		
		if(!afiliado){
			return new ResponseEntity<>("Beneficiario Invalido",HttpStatus.NOT_FOUND);
		}
		
		cuentaDestino = productoRepositorio.findById(beneficiario.getNumeroCuenta()).orElse(null);
		
		if(!cuentaDestino.getTipoProducto().getNombreTipoProducto().equals("Prestamo Bancario")){
			return new ResponseEntity<>("Cuenta del beneficiario No es de Prestamo",HttpStatus.BAD_REQUEST);
		}
		
		montoSuficiente = validarSaldoCuenta.saldoSuficiente(cuentaOrigen, montoTransferir);
		
		if(!montoSuficiente){
			return new ResponseEntity<>("El saldo de la cuenta No es suficiente",HttpStatus.CONFLICT);
		}
		
		pagoExitoso = pagoPrestamo.pagarPrestamo(cuentaOrigen, cuentaDestino, montoTransferir);
		
		if(pagoExitoso.length()<1){
			return new ResponseEntity<>("Ocurrió un error en la transferencia",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Transferencia No. "+pagoExitoso+" realizada de forma exitosa",HttpStatus.OK);
	}	
	

}
