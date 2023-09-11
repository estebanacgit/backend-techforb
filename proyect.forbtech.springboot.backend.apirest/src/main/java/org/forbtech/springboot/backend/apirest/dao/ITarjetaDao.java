package org.forbtech.springboot.backend.apirest.dao;

import org.forbtech.springboot.backend.apirest.Entity.Tarjeta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ITarjetaDao extends CrudRepository<Tarjeta, Long> {

}
