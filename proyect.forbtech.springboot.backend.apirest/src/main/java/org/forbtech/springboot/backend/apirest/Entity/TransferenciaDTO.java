package org.forbtech.springboot.backend.apirest.Entity;

public class TransferenciaDTO {
    private Long idOrigen;
    private Long idDestino;
    private Double montoTransferencia;
    private Long idBalance;
    private String descripcion;
    private String tipo;

    public Long getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Long getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public Double getMontoTransferencia() {
        return montoTransferencia;
    }

    public void setMontoTransferencia(Double montoTransferencia) {
        this.montoTransferencia = montoTransferencia;
    }

    public Long getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(Long idBalance) {
        this.idBalance = idBalance;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
