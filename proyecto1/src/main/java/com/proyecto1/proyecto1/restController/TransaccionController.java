package com.proyecto1.proyecto1.restController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.proyecto1.entidades.Producto;
import com.proyecto1.proyecto1.repositorio.ProductoRepositorio;
import com.proyecto1.proyecto1.repositorio.TransaccionRepositorio;
import com.proyecto1.proyecto1.utilitarias.PagoPrestamo;
import com.proyecto1.proyecto1.utilitarias.PagoTarjetaCredito;
import com.proyecto1.proyecto1.utilitarias.TransferirEfectivo;
import com.proyecto1.proyecto1.utilitarias.ValidarCuenta;
import com.proyecto1.proyecto1.utilitarias.ValidarSaldoCuenta;

@RestController
@RequestMapping("transaccion")
public class TransaccionController {
	
	@Autowired
	ProductoRepositorio productoRepositorio;
	
	@Autowired
	ValidarCuenta validarCuenta;
	
	@Autowired
	ValidarSaldoCuenta validarSaldoCuenta;
	
	@Autowired
	TransaccionRepositorio transaccionRepositorio;
	
	@Autowired
	TransferirEfectivo transferirEfectivo;
	
	@Autowired
	PagoTarjetaCredito pagoTarjetaCredito;
	
	@Autowired
	PagoPrestamo pagoPrestamo;
	
	@PostMapping("transferenciaEfectivo")
	public ResponseEntity<String> transferenciaEfectivo(HttpServletRequest request){
		
		Producto cuentaOrigen;
		Producto cuentaDestino;
		float montoTransferir;
		Boolean cuentasValidas =false;
		Boolean montoSuficiente = false;
		String transferenciaExitosa = "";
		
		String idCliente = "CL001";
		
		cuentaOrigen = productoRepositorio.findById(request.getParameter("idCuentaOrigen")).orElse(null);
		cuentaDestino = productoRepositorio.findById(request.getParameter("idCuentaDestino")).orElse(null);
		montoTransferir = Float.parseFloat(request.getParameter("monto"));
		
		cuentasValidas = validarCuenta.cuentasCliente(cuentaOrigen, cuentaDestino, idCliente);
		
		if(!(cuentasValidas&&(cuentaOrigen.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria") && 
				cuentaDestino.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria")))){
			return new ResponseEntity<>("Las cuentas No son válidas",HttpStatus.NOT_FOUND);
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
		float montoTransferir;
		Boolean cuentasValidas =false;
		Boolean montoSuficiente = false;
		String pagoExitoso = "";
		
		String idCliente = "CL001";
		
		cuentaOrigen = productoRepositorio.findById(request.getParameter("idCuentaOrigen")).orElse(null);
		cuentaDestino = productoRepositorio.findById(request.getParameter("idCuentaDestino")).orElse(null);
		montoTransferir = Float.parseFloat(request.getParameter("monto"));
		
		cuentasValidas = validarCuenta.cuentasCliente(cuentaOrigen, cuentaDestino, idCliente);
		
		if(!(cuentasValidas&&(cuentaOrigen.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria") && 
				cuentaDestino.getTipoProducto().getNombreTipoProducto().equals("Tarjeta de Crédito")))){
			return new ResponseEntity<>("Las cuentas No son válidas",HttpStatus.NOT_FOUND);
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
		float montoTransferir;
		Boolean cuentasValidas =false;
		Boolean montoSuficiente = false;
		String pagoExitoso = "";
		
		String idCliente = "CL001";
		
		cuentaOrigen = productoRepositorio.findById(request.getParameter("idCuentaOrigen")).orElse(null);
		cuentaDestino = productoRepositorio.findById(request.getParameter("idCuentaDestino")).orElse(null);
		montoTransferir = Float.parseFloat(request.getParameter("monto"));
		
		cuentasValidas = validarCuenta.cuentasCliente(cuentaOrigen, cuentaDestino, idCliente);
		
		if(!(cuentasValidas&&(cuentaOrigen.getTipoProducto().getNombreTipoProducto().equals("Cuenta Bancaria") && 
				cuentaDestino.getTipoProducto().getNombreTipoProducto().equals("Prestamo Bancario")))){
			return new ResponseEntity<>("Las cuentas No son válidas",HttpStatus.NOT_FOUND);
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
