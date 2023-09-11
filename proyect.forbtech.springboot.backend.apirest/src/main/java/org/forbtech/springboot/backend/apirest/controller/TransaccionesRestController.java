package org.forbtech.springboot.backend.apirest.controller;

import org.forbtech.springboot.backend.apirest.Entity.Transaccion;
import org.forbtech.springboot.backend.apirest.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransaccionesRestController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/transacciones")
    public List<Transaccion> listarTransacciones(){
        return clienteService.findAllTransaccion();
    }

    @GetMapping("/transaccion-id={id}")
    public Transaccion buscarTransaccionById(@PathVariable Long id){
        return clienteService.findTransaccionById(id);
    }

    @DeleteMapping("/borrar-transaccion-id={id}")
    public void eliminarTransaccionById(@PathVariable Long id){
        clienteService.deleteTransaccionById(id);
    }


}
