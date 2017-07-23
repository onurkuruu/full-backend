package com.oonurkuru.backend.dao;

import com.oonurkuru.backend.domains.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {


}
