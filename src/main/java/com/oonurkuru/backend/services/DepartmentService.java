package com.oonurkuru.backend.services;

import com.oonurkuru.backend.dto.DepartmentDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Onur Kuru on 26.7.2017.
 */

@Service
public interface DepartmentService {

    List<DepartmentDTO> findDepartmentByParameters(MultivaluedMap<String, String> queryMap) throws CustomException;

    DepartmentDTO findDepartmentById(Integer employeeId) throws CustomException;

    DepartmentDTO save(DepartmentDTO employee) throws CustomException;

    void delete(Integer departmentId) throws CustomException;
}
