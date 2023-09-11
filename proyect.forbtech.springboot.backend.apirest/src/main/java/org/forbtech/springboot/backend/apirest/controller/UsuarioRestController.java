package org.forbtech.springboot.backend.apirest.controller;

import org.forbtech.springboot.backend.apirest.Entity.*;
import org.forbtech.springboot.backend.apirest.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

   /*
    *
    *   En este controller de usuario se puede buscar, buscar por id, crear, eliminar
    *
    */

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/usuarios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Usuario> showUsuarios() {
        return clienteService.findAllusuarios();
    }

    @GetMapping("/usuarios/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Usuario findUsuario(@PathVariable Long id) {
        return clienteService.findUsuarioById(id);
    }

    @PostMapping("/usuarios")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {

        Map<String, Object> response = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        Balance balance = new Balance();

        balance.setSaldoActual(0.00);
        balance.setMoneda("Pesos");
        balance.setEstadoCuenta(true);
        balance.setDisponibilidadFondos(0.00);
        balance.setLimiteCuenta(0.00);
        balance.setFechaActualizacion(localDateTime);

        usuario.setBalance(balance);

        try {
            clienteService.saveUsuario(usuario);
            response.put("mensaje", "Usuario creado con exito");
            response.put("Usuario", usuario);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PutMapping("/usuarios/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Usuario editarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {

        Usuario usuarioActual = clienteService.findUsuarioById(id);
        Usuario usuarioUpdate = null;

        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setApellido(usuario.getApellido());
        usuarioActual.setPassword(usuario.getPassword());

        //verificar que las contraseñas al cambiarlas

        usuarioUpdate = clienteService.saveUsuario(usuarioActual);

        return usuarioUpdate;

    }

    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteUsuario(@PathVariable Long id) {
        //si se elimina un usuario, tambien se elimina su balance.
        clienteService.deleteUsuarioById(id);
        clienteService.deleteBalanceById(id);
    }

    @PostMapping("/realizar-transferencia={idOrigen}/{idDestino}/{montoTransferencia}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> realizarTrasferencia(@PathVariable Long idOrigen, @PathVariable Long idDestino, @PathVariable Double montoTransferencia) {
        clienteService.transferirEfectivo(idOrigen, idDestino, montoTransferencia);
        return ResponseEntity.ok("Transfererencia");
    }

    //En el otro envio mediante parametros los datos, aca envio mediante el body de la solicitud
    @PostMapping("/realizar-transferencia")
    public ResponseEntity<?> realizarTrasferenciaDTO(@RequestBody TransferenciaDTO transferenciaDTO) {

        // jeje
        Map<String, Object> response = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();

        try {

            Usuario usuarioOrigen = clienteService.findUsuarioById(transferenciaDTO.getIdOrigen());
            Usuario usuarioDestino = clienteService.findUsuarioById(transferenciaDTO.getIdDestino());

            if (usuarioOrigen != null && usuarioDestino != null) {
                Balance balance = clienteService.findBalanceById(transferenciaDTO.getIdBalance());

                Balance balanceOrigen = usuarioOrigen.getBalance();
                Balance balanceDestino = usuarioDestino.getBalance();

                if (balanceOrigen.getSaldoActual() >= transferenciaDTO.getMontoTransferencia()){
                    balanceOrigen.setSaldoActual(balanceOrigen.getSaldoActual() - transferenciaDTO.getMontoTransferencia());
                    balanceDestino.setSaldoActual(balanceDestino.getSaldoActual() + transferenciaDTO.getMontoTransferencia());

                } else {
                    response.put("Saldo Insuficiente", "El dinero en la cuenta es insuficiente");
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
                }

                Transaccion transaccion = new Transaccion();

                transaccion.setIdDestino(transferenciaDTO.getIdDestino());
                transaccion.setMontoTransferido(transferenciaDTO.getMontoTransferencia());
                transaccion.setBalance(balance);
                transaccion.setDescripcion("Transferencia Realizada");
                transaccion.setTipo("Transferencia");
                transaccion.setFecha(localDateTime);

                balanceOrigen.getTransacciones().add(transaccion);
                balanceDestino.getTransacciones().add(transaccion);

                clienteService.saveTransaccion(transaccion);
                clienteService.saveUsuario(usuarioOrigen);
                clienteService.saveUsuario(usuarioDestino);

                response.put("mensaje", "Transferencia exitosa");
                response.put("Datos Transferencia", transferenciaDTO);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } else {

                String errorMessage = "Los siguientes usuarios no existen: ";

                if (usuarioOrigen == null) {
                    errorMessage += "idOrigen ";
                }

                if (usuarioDestino == null) {
                    errorMessage += "idDestino ";
                }

                response.put("error", errorMessage);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/retirar-dinero={idUsuario}/{montoRetiro}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> retirarDinero(@PathVariable Long idUsuario, @PathVariable Double montoRetiro) {
        clienteService.retirarEfectivo(idUsuario, montoRetiro);
        return ResponseEntity.ok("Retiro");
    }

    @PostMapping("/retirar-dinero")
    public ResponseEntity<?> retirarDinero(@RequestBody TransferenciaDTO transferenciaDTO) {

        Map<String, Object> response = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();

        try {

            Usuario usuario = clienteService.findUsuarioById(transferenciaDTO.getIdOrigen());

            if (usuario != null) {

                Balance balance = usuario.getBalance();

                if (balance.getSaldoActual() >= transferenciaDTO.getMontoTransferencia()) {
                    balance.setSaldoActual(balance.getSaldoActual() - transferenciaDTO.getMontoTransferencia());

                    Transaccion transaccion = new Transaccion();
                    transaccion.setIdDestino(0L); //no hay destino por lo tanto coloco un 0 (CERO)
                    transaccion.setMontoTransferido(-transferenciaDTO.getMontoTransferencia());
                    transaccion.setBalance(balance);
                    transaccion.setDescripcion("Retiro Efectivo");
                    transaccion.setTipo("Retiro");
                    transaccion.setFecha(localDateTime);

                    balance.getTransacciones().add(transaccion);

                    clienteService.saveUsuario(usuario);

                    response.put("mensaje", "Retiro exitoso");
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

                } else {
                    response.put("Saldo insuficiente", "Dinero no disponible, saldo insuficiente.");
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                response.put("Usuario Inexistente", "El usuario no existe");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }

    @PostMapping("/deposito-dinero={idUsuario}/{montoDeposito}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> realizarDeposito(@PathVariable Long idUsuario, @PathVariable Double montoDeposito) {
        clienteService.realizarDeposito(idUsuario, montoDeposito);
        return ResponseEntity.ok("Desposito");
    }

    @PostMapping("/deposito-dinero")
    public ResponseEntity<?> realizarDeposito(@RequestBody TransferenciaDTO transferenciaDTO) {

        Map<String, Object> response = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.now();

        try {

            Usuario usuario = clienteService.findUsuarioById(transferenciaDTO.getIdOrigen());

            if (usuario != null) {

                Balance balance = usuario.getBalance();

                balance.setSaldoActual(balance.getSaldoActual() + transferenciaDTO.getMontoTransferencia());

                Transaccion transaccion = new Transaccion();
                transaccion.setIdDestino(0L); //no hay destino por lo tanto coloco un 0 (CERO)
                transaccion.setMontoTransferido(transferenciaDTO.getMontoTransferencia());
                transaccion.setBalance(balance);
                transaccion.setDescripcion("Deposito Efectivo");
                transaccion.setTipo("Deposito");
                transaccion.setFecha(localDateTime);

                balance.getTransacciones().add(transaccion);

                clienteService.saveUsuario(usuario);

                response.put("mensaje", "Deposito exitoso");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

            } else {
                response.put("Usuario Inexistente", "El Usuario No existe");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
