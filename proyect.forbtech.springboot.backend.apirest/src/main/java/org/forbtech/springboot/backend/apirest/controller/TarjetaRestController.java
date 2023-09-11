package org.forbtech.springboot.backend.apirest.controller;

import org.forbtech.springboot.backend.apirest.Entity.Tarjeta;
import org.forbtech.springboot.backend.apirest.Entity.TarjetaDTO;
import org.forbtech.springboot.backend.apirest.Entity.Usuario;
import org.forbtech.springboot.backend.apirest.Entity.UsuarioTarjeta;
import org.forbtech.springboot.backend.apirest.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TarjetaRestController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping("/asignar-tarjeta={idTarjeta}/usuario={idUsuario}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> crearAsignarTarjetaUsuario(@PathVariable Long idUsuario, @PathVariable Long idTarjeta){

        try {
            clienteService.asignarTarjetaUsuario(idUsuario, idTarjeta);
            return ResponseEntity.ok("Tarjeta asignada exitosamente al usuario.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se pudo asignar la tarjeta al usuario: " + e.getMessage());
        }

    }

    @PostMapping("/asignar-tarjeta")
    public ResponseEntity<?> crearAsignarTarjetaUsuario(@RequestBody TarjetaDTO tarjetaDTO){

        Map<String, Object> response = new HashMap<>();

        try {

            Usuario usuario = clienteService.findUsuarioById(tarjetaDTO.getIdUsuario());
            Tarjeta tarjeta = clienteService.findTarjetaById(tarjetaDTO.getIdTarjeta());

            if (usuario != null && tarjeta != null) {

                UsuarioTarjeta usuarioTarjeta = new UsuarioTarjeta();
                usuarioTarjeta.setUsuario(usuario);
                usuarioTarjeta.setTarjeta(tarjeta);

                usuario.getUsuarioTarjetas().add(usuarioTarjeta);
                //tarjeta.getUsuarioTarjetas().add(usuarioTarjeta);

                clienteService.saveUsuario(usuario);
                clienteService.saveTarjeta(tarjeta);

                response.put("Tarjeta Asignada", "Tarjeta Asignada al Usuario Correctamente!");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);


            } else {

                String errorMessage = "Los siguientes datos no existen: ";

                if (usuario == null) {
                    errorMessage += "Usuario ";
                }

                if (tarjeta == null) {
                    errorMessage += "Tarjeta ";
                }

                response.put("error", errorMessage);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

            }

        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/tarjetas")
    public List<Tarjeta> obtenerTarjetas(){
        return clienteService.findAllTarjetas();
    }

    @GetMapping("/tarjeta-id={id}")
    public Tarjeta obtenerTarjetaId(@PathVariable Long id){
        return clienteService.findTarjetaById(id);
    }

    @PostMapping("/tarjeta")
    public Tarjeta crearTarjeta(@RequestBody Tarjeta tarjeta){
        return clienteService.saveTarjeta(tarjeta);
    }

    @DeleteMapping("/tarjeta-id={id}")
    public void eliminarTarjeta(@PathVariable Long id){
        clienteService.deleteTarjetaById(id);
    }

    @PutMapping("/tarjeta-id={id}")
    public Tarjeta editarTarjeta(@RequestBody Tarjeta tarjeta, @PathVariable Long id){
        Tarjeta tarjetaActual = clienteService.findTarjetaById(id);
        Tarjeta tarjetaNueva = null;

        tarjetaActual.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
        tarjetaActual.setTipoTarjeta(tarjeta.getTipoTarjeta());
        tarjetaActual.setNombreTarjeta(tarjeta.getNombreTarjeta());
        tarjetaActual.setFechaVencimiento(tarjeta.getFechaVencimiento());

        tarjetaNueva = clienteService.saveTarjeta(tarjetaActual);

        return tarjetaNueva;

    }

}
