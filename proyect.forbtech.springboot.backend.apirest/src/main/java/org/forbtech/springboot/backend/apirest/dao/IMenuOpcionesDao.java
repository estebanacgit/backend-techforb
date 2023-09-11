package org.forbtech.springboot.backend.apirest.dao;

import org.forbtech.springboot.backend.apirest.Entity.MenuOpciones;
import org.springframework.data.repository.CrudRepository;

public interface IMenuOpcionesDao extends CrudRepository<MenuOpciones, Long> {
}
