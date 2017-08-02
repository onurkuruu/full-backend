package com.oonurkuru.backend.domains;

import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.dto.EmployeeDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur Kuru on 21.7.2017.
 */

@Entity
@Table
@Mapper(targetClass = EmployeeDTO.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Integer id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "SALARY")
    private Integer salary;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne
    @JoinColumn(name = "ROLE")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT")
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EMPLOYEE_PROJECT",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID"))
    private List<Project> projects;

    public Employee() {
        this.projects = new ArrayList<>();
    }

    public Employee(String username, String firstName, String lastName, Integer age, Integer salary, Role role, Department department, List<Project> projects) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.role = role;
        this.department = department;
        this.projects = projects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
