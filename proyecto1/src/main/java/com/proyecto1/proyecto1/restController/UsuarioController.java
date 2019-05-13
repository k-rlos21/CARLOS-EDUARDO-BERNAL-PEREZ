package com.proyecto1.proyecto1.restController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.proyecto1.entidades.Usuario;
import com.proyecto1.proyecto1.excepciones.UsuarioErrorResponse;
import com.proyecto1.proyecto1.excepciones.UsuarioException;
import com.proyecto1.proyecto1.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	/*@GetMapping("test")
	public String metodoPrueba(){
		
		return "Funcionando Bien";
	}*/
	
	
	@PostMapping("validarCredenciales")
	public ResponseEntity<String> validarUsuario(HttpServletRequest request){
		
		String contrasenia = request.getParameter("nombreusuario")+request.getParameter("contrasenia");
		
		String nombreUsuario = request.getParameter("nombreusuario");
		
		Usuario usuario  = usuarioRepositorio.findByNombreUsuario(nombreUsuario);
		
		if(usuario==null){
			return new ResponseEntity<>("Usuario No encontrado",HttpStatus.NOT_FOUND);
		}
		
		if(usuario.getIntentos() > 4){
			return new ResponseEntity<String>("Usuario Inactivo, favor contactar con soporte",HttpStatus.BAD_REQUEST);
		}
		
		if(!usuario.getContrasenia().equals(contrasenia)){
			
			usuario.setIntentos(usuario.getIntentos()+1);
			usuarioRepositorio.save(usuario);

			return new ResponseEntity<String>("El usuario no esta autorizado",HttpStatus.UNAUTHORIZED);
			
		}
		
		
		return new ResponseEntity<String>("Usuario Validado de forma exitosa",HttpStatus.OK);
		
	}
	
	/*@ExceptionHandler
	public ResponseEntity<UsuarioErrorResponse> handleException(UsuarioException exc){
		
		UsuarioErrorResponse error = new UsuarioErrorResponse(
								HttpStatus.UNAUTHORIZED.value(),
								exc.getMessage(),
								System.currentTimeMillis()
				);
		
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	}*/
}
