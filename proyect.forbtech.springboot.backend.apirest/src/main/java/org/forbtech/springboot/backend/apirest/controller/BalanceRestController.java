package org.forbtech.springboot.backend.apirest.controller;

import org.forbtech.springboot.backend.apirest.Entity.Balance;
import org.forbtech.springboot.backend.apirest.Entity.Usuario;
import org.forbtech.springboot.backend.apirest.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BalanceRestController {

    @Autowired
    private IClienteService clienteService;

    /*
    Obtener balances por ID
    Obtener todos los balances.
    Eliminar un balance
    Editar un balance
     */

    @GetMapping("/obtener-balances")
    public List<Balance> obtenerBalances() {
         return clienteService.findAllBalances();
    }

    @GetMapping("/obtener-balance={id}")
    public Balance obtenerBalanceId(@PathVariable Long id) {
        return clienteService.findBalanceById(id);
    }

    @DeleteMapping("/eliminar-balance={id}")
    public void eliminarBalanceId(@PathVariable Long id){
        clienteService.deleteBalanceById(id);
    }

    @PutMapping("/editar-balance")
    public Balance editarBalance(@RequestBody Balance balance, @PathVariable Long id){
        Balance balanceActual = clienteService.findBalanceById(id);
        Balance nuevoBalance = null;

        balanceActual.setMoneda(balance.getMoneda());
        balanceActual.setSaldoActual(balance.getSaldoActual());
        balanceActual.setDisponibilidadFondos(balance.getDisponibilidadFondos());
        balanceActual.setEstadoCuenta(balance.getEstadoCuenta());

        nuevoBalance = clienteService.saveBalance(balanceActual);

        return nuevoBalance;
    }


}
