package org.forbtech.springboot.backend.apirest.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario_tarjeta")
public class UsuarioTarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioTarjetaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    private Tarjeta tarjeta;

    public Long getUsuarioTarjetaId() {
        return usuarioTarjetaId;
    }

    public void setUsuarioTarjetaId(Long usuarioTarjetaId) {
        this.usuarioTarjetaId = usuarioTarjetaId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
}
