package org.forbtech.springboot.backend.apirest.dao;

import org.forbtech.springboot.backend.apirest.Entity.Transaccion;
import org.springframework.data.repository.CrudRepository;

public interface ITransaccionDao extends CrudRepository<Transaccion, Long> {
}
