package org.forbtech.springboot.backend.apirest.exception;

public class TarjetaNotFoundException extends RuntimeException {

    public TarjetaNotFoundException(Long idTarjeta) {
        super("Tarjeta con ID " + idTarjeta + " no encontrado.");
    }
}