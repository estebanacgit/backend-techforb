package org.forbtech.springboot.backend.apirest.Service;

import org.forbtech.springboot.backend.apirest.Entity.*;
import org.forbtech.springboot.backend.apirest.dao.*;
import org.forbtech.springboot.backend.apirest.exception.SaldoInsuficienteException;
import org.forbtech.springboot.backend.apirest.exception.TarjetaNotFoundException;
import org.forbtech.springboot.backend.apirest.exception.UsuarioNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private IBalanceDao balanceDao;

    @Autowired
    private IMenuOpcionesDao menuOpciones;

    @Autowired
    private ITarjetaDao tarjetaDao;

    @Autowired
    private ITransaccionDao transaccionDao;

    @Autowired
    private IUsuarioTarjetaDao usuarioTarjetaDao;

    //USUARIO

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllusuarios() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    public void deleteUsuarioById(Long id) {
        usuarioDao.deleteById(id);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findUsuarioById(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public void asignarTarjetaUsuario(Long idUsuario, Long idTarjeta) {
        Usuario usuario = usuarioDao.findById(idUsuario).orElseThrow(() -> new UsuarioNotFoundException(idUsuario));
        Tarjeta tarjeta = tarjetaDao.findById(idTarjeta).orElseThrow(() -> new TarjetaNotFoundException(idTarjeta));

        UsuarioTarjeta usuarioTarjeta = new UsuarioTarjeta();
        usuarioTarjeta.setUsuario(usuario);
        usuarioTarjeta.setTarjeta(tarjeta);

        usuario.getUsuarioTarjetas().add(usuarioTarjeta);
        //tarjeta.getUsuarioTarjetas().add(usuarioTarjeta);

        usuarioDao.save(usuario);
        tarjetaDao.save(tarjeta);

    }




    @Override
    public void transferirEfectivo(Long idOrigen, Long idDestino, Double montoTransferencia) {
        Usuario usuarioOrigen = usuarioDao.findById(idOrigen).orElseThrow(() -> new UsuarioNotFoundException(idOrigen));
        Usuario usuarioDestio = usuarioDao.findById(idDestino).orElseThrow(() -> new UsuarioNotFoundException(idDestino));

        Balance balanceOrigen = usuarioOrigen.getBalance();
        Balance balanceDestino = usuarioDestio.getBalance();

        if (balanceOrigen.getSaldoActual() >= montoTransferencia){
            balanceOrigen.setSaldoActual(balanceOrigen.getSaldoActual() - montoTransferencia);
            balanceDestino.setSaldoActual(balanceDestino.getSaldoActual() + montoTransferencia);
        }

        Transaccion transaccion = new Transaccion();

        transaccion.setIdDestino(idDestino);
        transaccion.setMontoTransferido(montoTransferencia);

        balanceOrigen.getTransacciones().add(transaccion);
        balanceDestino.getTransacciones().add(transaccion);

        transaccionDao.save(transaccion);
        usuarioDao.save(usuarioOrigen);
        usuarioDao.save(usuarioDestio);

    }


    public void retirarEfectivo(Long idUsuario, Double montoRetiro){
        Usuario usuario = usuarioDao.findById(idUsuario).orElseThrow(() -> new UsuarioNotFoundException(idUsuario));
        Balance balance = usuario.getBalance();

        if (balance.getSaldoActual() >= montoRetiro) {
            balance.setSaldoActual(balance.getSaldoActual() - montoRetiro);

            Transaccion transaccion = new Transaccion();
            transaccion.setIdDestino(idUsuario);
            transaccion.setMontoTransferido(-montoRetiro);

            balance.getTransacciones().add(transaccion);

            usuarioDao.save(usuario);
        } else {
            System.out.println("Saldo insuficiente");
            //throw new SaldoInsuficienteException(idUsuario, montoRetiro);
        }
    }

    @Override
    public void realizarDeposito(Long idUsuario, Double montoDeposito) {
        Usuario usuario = usuarioDao.findById(idUsuario).orElseThrow(() -> new UsuarioNotFoundException(idUsuario));
        Balance balance = usuario.getBalance();

        balance.setSaldoActual(balance.getSaldoActual() + montoDeposito);

        Transaccion transaccion = new Transaccion();
        transaccion.setIdDestino(idUsuario);
        transaccion.setMontoTransferido(montoDeposito);

        balance.getTransacciones().add(transaccion);

        usuarioDao.save(usuario);

    }

    //TARJETA

    @Override
    @Transactional(readOnly = true)
    public Tarjeta findTarjetaById(Long id) {
        return tarjetaDao.findById(id).orElse(null);
    }

    @Override
    public Balance findBalanceById(Long id) {
        return balanceDao.findById(id).orElse(null);
    }

    @Override
    public List<Balance> findAllBalances() {
        return (List<Balance>) balanceDao.findAll();
    }

    @Override
    public void deleteBalanceById(Long id) {
        balanceDao.deleteById(id);
    }

    @Override
    public Balance saveBalance(Balance balance) {
        return balanceDao.save(balance);
    }

    @Override
    public Transaccion saveTransaccion(Transaccion transaccion) {
        return transaccionDao.save(transaccion);
    }

    @Override
    public void deleteTransaccionById(Long id) {
        transaccionDao.deleteById(id);
    }

    @Override
    public List<Transaccion> findAllTransaccion() {
        return (List<Transaccion>) transaccionDao.findAll();
    }

    @Override
    public Transaccion findTransaccionById(Long id) {
        return transaccionDao.findById(id).orElse(null);
    }

    @Override
    public Usuario findByUsername(String name) {
        return usuarioDao.findByUsername(name);
    }

    @Override
    public Tarjeta saveTarjeta(Tarjeta tarjeta) {
        return tarjetaDao.save(tarjeta);
    }

    @Override
    public List<Tarjeta> findAllTarjetas() {
        return (List<Tarjeta>) tarjetaDao.findAll();
    }

    @Override
    public void deleteTarjetaById(Long id) {
        tarjetaDao.deleteById(id);
    }

}
