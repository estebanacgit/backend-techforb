package org.forbtech.springboot.backend.apirest.Service;

import org.forbtech.springboot.backend.apirest.Entity.*;

import java.util.List;

public interface IClienteService {

    //CONTRATO USUARIOS
    List<Usuario> findAllusuarios();

    void deleteUsuarioById(Long id);

    Usuario saveUsuario(Usuario usuario);

    Usuario findUsuarioById(Long id);

    //CONTRATOS VOID
    void asignarTarjetaUsuario(Long idUsuario, Long idTarjeta);

    void transferirEfectivo(Long idOrigen, Long idDestino, Double montoTransferencia);

    void retirarEfectivo(Long idUsuario, Double montoRetiro);

    void realizarDeposito(Long idUsuario, Double montoDeposito);

    //CONTATO TARJETA
    Tarjeta findTarjetaById(Long id);

    Tarjeta saveTarjeta(Tarjeta tarjeta);

    List<Tarjeta> findAllTarjetas();

    void deleteTarjetaById(Long id);

    //CONTRATO BALANCE
    Balance findBalanceById(Long id);

    List<Balance> findAllBalances();

    void deleteBalanceById(Long id);

    Balance saveBalance (Balance balance);

    //CONTRATO TRANSACCION
    Transaccion saveTransaccion(Transaccion transaccion);

    void deleteTransaccionById(Long id);

    List<Transaccion> findAllTransaccion();

    Transaccion findTransaccionById(Long id);

    Usuario findByUsername(String name);
}
