package com.oonurkuru.backend.services;

import com.oonurkuru.backend.dto.DepartmentDTO;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Onur Kuru on 26.7.2017.
 */

public interface DepartmentService {

    default public List<DepartmentDTO> findDepartmentByParameters(MultivaluedMap<String, String> queryMap) {
        //Log
        return null;
    }

    default public DepartmentDTO findDepartmentById(Integer employeeId) {
        //Log
        return null;
    }

    default public DepartmentDTO save(DepartmentDTO employee) {
        //Log
        return null;
    }

    default public void delete(Integer departmentId) {
        //Log
    }
}
