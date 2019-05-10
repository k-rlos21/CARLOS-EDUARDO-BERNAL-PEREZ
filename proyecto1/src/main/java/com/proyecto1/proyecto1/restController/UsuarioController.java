package com.proyecto1.proyecto1.restController;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto1.proyecto1.entidades.Usuario;
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
	public String validarUsuario(HttpServletRequest request){
		
		String contrasenia = request.getParameter("nombreusuario") + request.getParameter("contrasenia");
		
		int idUsuario = Integer.parseInt(request.getParameter("idusuario"));
		
		Optional<Usuario> us = usuarioRepositorio.findById(idUsuario);
		Usuario usuario  = us.get();
		
		if(usuario.getContrasenia() == contrasenia){
			return "Usuario Validado de manera exitosa";
		}
		else{
			return "Usuario Invalido";
		}
		
	}
}
