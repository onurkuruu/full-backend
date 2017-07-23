package com.oonurkuru.backend.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.dto.ProjectDTO;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Onur Kuru on 21.7.2017.
 */

@Entity
@Table(name = "PROJECT")
@Mapper(targetClass = ProjectDTO.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    private List<Employee> employeeList;

    public Project() {
    }

    public Project(String name, List<Employee> employeeList) {
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
