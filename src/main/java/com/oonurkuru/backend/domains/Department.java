package com.oonurkuru.backend.domains;

import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.dto.DepartmentDTO;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Onur Kuru on 21.7.2017.
 */

@Entity
@Table(name = "DEPARTMENT")
@Mapper(targetClass = DepartmentDTO.class)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employeeList;

    public Department() {
    }

    public Department(String name, List<Employee> employeeList) {
        this.name = name;
        this.employeeList = employeeList;
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

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
