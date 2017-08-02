package com.oonurkuru.backend.services;

import com.oonurkuru.backend.dto.EmployeeDTO;
import com.oonurkuru.backend.dto.ProjectDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Employee CRUD işlemleri için arayüz
 */

@Service
public interface EmployeeService {

    EmployeeDTO findEmployeeByUsername(String username) throws CustomException;

    List<EmployeeDTO> findEmployeeByParameters(MultivaluedMap<String, String> queryMap) throws CustomException;

    EmployeeDTO findEmployeeById(Integer employeeId) throws CustomException;

    EmployeeDTO save(EmployeeDTO employee) throws CustomException;

    void delete(Integer employeeId) throws CustomException;

    EmployeeDTO addProjectToEmployee(Integer employeeId, ProjectDTO projectDTO) throws CustomException;

    EmployeeDTO deleteProjectFromEmployee(Integer employeeId, Integer projectId) throws CustomException;

}
