package org.forbtech.springboot.backend.apirest.dao;

import org.forbtech.springboot.backend.apirest.Entity.UsuarioTarjeta;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioTarjetaDao extends CrudRepository<UsuarioTarjeta, Long> {
}
