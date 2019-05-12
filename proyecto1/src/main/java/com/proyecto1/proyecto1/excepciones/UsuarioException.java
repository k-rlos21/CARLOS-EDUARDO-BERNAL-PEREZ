package com.proyecto1.proyecto1.excepciones;

public class UsuarioException extends RuntimeException {
	
	public UsuarioException() {
	}

	public UsuarioException(String arg0) {
		super(arg0);
	}

	public UsuarioException(Throwable arg0) {
		super(arg0);
	}

	public UsuarioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UsuarioException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
