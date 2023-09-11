package org.forbtech.springboot.backend.apirest.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(Long idUsuario) {
        super("Usuario con ID " + idUsuario + " no encontrado.");
    }
}