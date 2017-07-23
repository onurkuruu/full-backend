package com.oonurkuru.backend.services;

import com.oonurkuru.backend.dto.EmployeeDTO;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Employee CRUD işlemleri için arayüz
 */
public interface EmployeeService {

    default public List<EmployeeDTO> findEmployeeByParameters(MultivaluedMap<String, String> queryMap) {
        //Log
        return null;
    }

    default public EmployeeDTO findEmployeeById(Integer employeeId) {
        //Log
        return null;
    }

    default public EmployeeDTO save(EmployeeDTO employee) {
        //Log
        return null;
    }

    default public void delete(Integer employeeId) {
        //Log
    }

}
