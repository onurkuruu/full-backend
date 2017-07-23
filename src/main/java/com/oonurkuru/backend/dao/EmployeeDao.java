package com.oonurkuru.backend.dao;

import com.oonurkuru.backend.domains.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer>, JpaRepository<Employee, Integer> {


}
