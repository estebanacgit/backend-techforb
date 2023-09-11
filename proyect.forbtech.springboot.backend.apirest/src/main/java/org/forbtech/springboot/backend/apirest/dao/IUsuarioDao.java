package org.forbtech.springboot.backend.apirest.dao;

import org.forbtech.springboot.backend.apirest.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

}
