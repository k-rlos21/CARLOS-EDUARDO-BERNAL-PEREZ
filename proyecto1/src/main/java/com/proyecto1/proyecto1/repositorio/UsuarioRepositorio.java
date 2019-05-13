package com.proyecto1.proyecto1.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.proyecto1.proyecto1.entidades.Usuario;

public interface UsuarioRepositorio extends CrudRepository<Usuario, Integer>{
	
	Usuario findByNombreUsuario(String nombreUsuario);

}
