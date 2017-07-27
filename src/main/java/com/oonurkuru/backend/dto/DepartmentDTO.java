package com.oonurkuru.backend.dto;

import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.domains.Department;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

@Mapper(targetClass = Department.class)
public class DepartmentDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "İsim alanı Boş olamaz")
    @Size(min = 3, max = 30, message = "İsim alanı en az 3, en çok 30 karakterden oluşmalıdır.")
    private String name;

    private List<EmployeeDTO> employeeList;

    public DepartmentDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeDTO> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeDTO> employeeList) {
        this.employeeList = employeeList;
    }
}
