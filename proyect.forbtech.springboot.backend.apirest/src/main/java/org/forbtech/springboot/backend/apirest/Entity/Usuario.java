package org.forbtech.springboot.backend.apirest.Entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 60)
    private String password;

    private Boolean enabled;

    private String nombre;
    private String apellido;

    @NotEmpty(message = "{user.email.notEmpty}")
    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private Balance balance;

    //@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    //private Transaccion transaccion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private Set<UsuarioTarjeta> usuarioTarjetas = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MenuOpciones> menuOpciones = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
        balance.setUsuario(this);
    }

    /*
    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
        transaccion.setUsuario(this);
    }
    */

    public Set<UsuarioTarjeta> getUsuarioTarjetas() {
        return usuarioTarjetas;
    }

    public void setUsuarioTarjetas(Set<UsuarioTarjeta> usuarioTarjetas) {
        this.usuarioTarjetas = usuarioTarjetas;
    }

    public Set<MenuOpciones> getMenuOpciones() {
        return menuOpciones;
    }

    public void setMenuOpciones(Set<MenuOpciones> menuOpciones) {
        this.menuOpciones = menuOpciones;
    }

    private static final long serialVersionUID = 1L;
}
