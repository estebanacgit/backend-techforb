package org.forbtech.springboot.backend.apirest.dao;

import org.forbtech.springboot.backend.apirest.Entity.Balance;
import org.springframework.data.repository.CrudRepository;

public interface IBalanceDao extends CrudRepository<Balance,Long> {
}
