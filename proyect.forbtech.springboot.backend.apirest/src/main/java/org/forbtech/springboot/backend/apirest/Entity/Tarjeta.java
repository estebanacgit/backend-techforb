package org.forbtech.springboot.backend.apirest.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tarjetas")
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long idTarjeta;

    //Si es debito, credito
    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta;

    //el numero en formato "XXXX XXXX XXXX XXXX"
    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;

    //si es visa, maastercard, american express, etc
    @Column(name = "nombre_tarjeta")
    private String nombreTarjeta;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    //asigno un usuario a la tarjeta
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER , mappedBy = "tarjeta")
    @JsonBackReference
    private Set<UsuarioTarjeta> usuarioTarjetas = new HashSet<>();

    public Long getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public Set<UsuarioTarjeta> getUsuarioTarjetas() {
        return usuarioTarjetas;
    }

    public void setUsuarioTarjetas(Set<UsuarioTarjeta> usuarioTarjetas) {
        this.usuarioTarjetas = usuarioTarjetas;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
