package org.forbtech.springboot.backend.apirest.exception;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String nombreUsuario) {
        super("Saldo insuficiente para el usuario: " + nombreUsuario);
    }
}
