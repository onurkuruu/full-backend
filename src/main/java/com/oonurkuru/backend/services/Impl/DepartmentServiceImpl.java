package com.oonurkuru.backend.services.Impl;

import com.oonurkuru.backend.dao.DepartmentDao;
import com.oonurkuru.backend.dao.EmployeeDao;
import com.oonurkuru.backend.domains.Department;
import com.oonurkuru.backend.dto.DepartmentDTO;
import com.oonurkuru.backend.dto.EmployeeDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.DepartmentService;
import com.oonurkuru.backend.utils.Mapper;
import com.oonurkuru.backend.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Onur Kuru on 26.7.2017.
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    final private DepartmentDao departmentDao;
    final private EmployeeDao employeeDao;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao, EmployeeDao employeeDao) {
        this.departmentDao = departmentDao;
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findDepartmentByParameters(MultivaluedMap<String, String> queryMap) throws CustomException {

        PageRequest pageRequest = QueryUtils.createPageRequest(queryMap);
        Example<Department> example = QueryUtils.createExample(queryMap, Department.class);
        List<Department> departmentList;

        try {
            departmentList = departmentDao.findAll(example, pageRequest).getContent();
        } catch (Exception e) {
            throw new CustomException(201, "Query Error", "Sorgu Çalıştırılamadı.");
        }

        return (List<DepartmentDTO>) Mapper.objectMapper(departmentList, true);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO findDepartmentById(Integer departmentId) throws CustomException {

        Department department;

        try {
            department = departmentDao.findOne(departmentId);
        } catch (Exception e) {
            throw new CustomException(600, "Unhandle Error", e.getMessage());
        }

        return (DepartmentDTO) Mapper.objectMapper(department, true);
    }

    @Override
    @Transactional
    public DepartmentDTO save(DepartmentDTO departmentDTO) throws CustomException {

        Department department = (Department) Mapper.objectMapper(departmentDTO, false);
        department.setEmployeeList(new ArrayList<>());

        List<Integer> idList = departmentDTO.getEmployeeList().stream()
                .map(EmployeeDTO::getId).collect(Collectors.toList());

        department.setEmployeeList(employeeDao.findAll(idList));

        try {
            departmentDao.save(department);
        } catch (Exception e) {
            throw new CustomException(100, "Save Entity Error", e.getMessage());
        }

        return (DepartmentDTO) Mapper.objectMapper(department, true);
    }

    @Override
    @Transactional
    public void delete(Integer departmentId) {

        try {
            departmentDao.delete(departmentId);
        } catch (Exception e) {
            throw new CustomException(102, "Delete Entity Error", e.getMessage());
        }
    }
}
