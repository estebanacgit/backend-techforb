package org.forbtech.springboot.backend.apirest.Entity;

import java.util.Date;

public class TarjetaDTO {
    private Long idTarjeta;

    private Long idUsuario;

    public Long getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
