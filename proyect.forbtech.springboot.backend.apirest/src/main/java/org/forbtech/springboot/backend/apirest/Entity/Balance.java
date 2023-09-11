package org.forbtech.springboot.backend.apirest.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_balance")
    private Long idBalance;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Usuario usuario;

    @Column(name = "saldo_actual")
    private Double saldoActual;

    private String moneda;

    @Column(name = "estado_cuenta")
    private Boolean estadoCuenta;

    @Column(name = "disponibilidad_fondos")
    private Double disponibilidadFondos;

    @Column(name = "limite_cuenta")
    private Double limiteCuenta;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "balance")
    private List<Transaccion> transacciones;

    public Long getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(Long idBalance) {
        this.idBalance = idBalance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Boolean getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(Boolean estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public Double getDisponibilidadFondos() {
        return disponibilidadFondos;
    }

    public void setDisponibilidadFondos(Double disponibilidadFondos) {
        this.disponibilidadFondos = disponibilidadFondos;
    }

    public Double getLimiteCuenta() {
        return limiteCuenta;
    }

    public void setLimiteCuenta(Double limiteCuenta) {
        this.limiteCuenta = limiteCuenta;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}
