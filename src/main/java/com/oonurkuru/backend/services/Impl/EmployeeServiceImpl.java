package com.oonurkuru.backend.services.Impl;

import com.oonurkuru.backend.dao.DepartmentDao;
import com.oonurkuru.backend.dao.EmployeeDao;
import com.oonurkuru.backend.dao.ProjectDao;
import com.oonurkuru.backend.dao.RoleDao;
import com.oonurkuru.backend.domains.Employee;
import com.oonurkuru.backend.domains.Project;
import com.oonurkuru.backend.dto.EmployeeDTO;
import com.oonurkuru.backend.dto.ProjectDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.EmployeeService;
import com.oonurkuru.backend.utils.Mapper;
import com.oonurkuru.backend.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * EmployeeService sınıfının implementesidir. Employee ile ilgili CRUD işlemlerini içerir.
 */
@Component
public class EmployeeServiceImpl implements EmployeeService {

    final private EmployeeDao employeeDao;
    final private DepartmentDao departmentDao;
    final private ProjectDao projectDao;
    final private RoleDao roleDao;
    final private PasswordEncoder passwordEncoder;

    /**
     * Bağımlılıklar yükleniyor
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao, DepartmentDao departmentDao, ProjectDao projectDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.employeeDao = employeeDao;
        this.departmentDao = departmentDao;
        this.projectDao = projectDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO findEmployeeByUsername(String username) throws CustomException {

        Employee employee;
        EmployeeDTO employeeDTO;

        try {
            employee = employeeDao.findEmployeeByUsername(username);
            employeeDTO = (EmployeeDTO) Mapper.objectMapper(employee, true);
        } catch (Exception e) {
            throw new CustomException(600, "Unhandled Error", e.getMessage());
        }

        return employeeDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findEmployeeByParameters(MultivaluedMap<String, String> queryMap) throws CustomException {

        PageRequest pageRequest = QueryUtils.createPageRequest(queryMap);
        Example<Employee> example = QueryUtils.createExample(queryMap, Employee.class);

        List<Employee> employeeList;

        try {
            employeeList = employeeDao.findAll(example, pageRequest).getContent();
        } catch (Exception e) {
            throw new CustomException(201, "Query Error", "Sorgu Çalıştırılamadı");
        }

        return (List<EmployeeDTO>) Mapper.objectMapper(employeeList, true);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO findEmployeeById(Integer employeeId) throws CustomException {

        Employee employee;
        EmployeeDTO employeeDTO;

        try {
            employee = employeeDao.findOne(employeeId);
            employeeDTO = (EmployeeDTO) Mapper.objectMapper(employee, true);
        } catch (Exception e) {
            throw new CustomException(600, "Unhandled Error", e.getMessage());
        }

        return employeeDTO;
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) throws CustomException {

        Employee employee = (Employee) Mapper.objectMapper(employeeDTO, false);

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setDepartment(departmentDao.findOne(employeeDTO.getDepartment().getId()));
        employee.setRole(roleDao.findOne(employeeDTO.getRole().getId()));
        employee.setProjects(new ArrayList<>());
        employeeDTO.getProjects().forEach(project -> employee.getProjects().add(projectDao.findOne(project.getId())));

        try {
            employeeDao.save(employee);
        } catch (Exception e) {
            throw new CustomException(100, "Entity Save Error", e.getMessage());
        }

        return (EmployeeDTO) Mapper.objectMapper(employee, true);
    }

    @Override
    public void delete(Integer employeeId) {

        try {
            employeeDao.delete(employeeId);
        } catch (Exception e) {
            throw new CustomException(102, "Entity Delete Error", e.getMessage());
        }
    }

    @Override
    @Transactional
    public EmployeeDTO addProjectToEmployee(Integer employeeId, ProjectDTO projectDTO) throws CustomException {

        Project project;
        Employee employee;
        try {
            project = projectDao.findOne(projectDTO.getId());
            employee = employeeDao.findOne(employeeId);
        } catch (Exception e) {
            throw new CustomException(102, "Entity Find Error", e.getMessage());
        }

        employee.getProjects().add(project);
        employee = employeeDao.save(employee);

        return (EmployeeDTO) Mapper.objectMapper(employee, true);
    }

    @Override
    @Transactional
    public EmployeeDTO deleteProjectFromEmployee(Integer employeeId, Integer projectId) throws CustomException {

        Project project;
        Employee employee;

        try {
            project = projectDao.findOne(projectId);
            employee = employeeDao.findOne(employeeId);
        } catch (Exception e) {
            throw new CustomException(102, "Entity Find Error", e.getMessage());
        }

        employee.getProjects().remove(project);
        employee = employeeDao.save(employee);

        return (EmployeeDTO) Mapper.objectMapper(employee, true);
    }
}
