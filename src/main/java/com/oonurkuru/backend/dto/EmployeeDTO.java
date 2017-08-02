package com.oonurkuru.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.domains.Employee;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

@Mapper(targetClass = Employee.class)
public class EmployeeDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Kullanıcı Adı alanı boş olamaz")
    @Size(min = 1, max = 30, message = "Kullanıcı Adı en fazla 30 karakter içermelidir.")
    private String username;

    @NotEmpty(message = "İsim alanı boş olamaz")
    @Size(min = 1, max = 30, message = "İsim en fazla 30 karakter içermelidir.")
    private String firstName;

    @NotEmpty(message = "Soyisim alanı boş olamaz")
    @Size(min = 1, max = 30, message = "Soyisim en fazla 30 karakter içermelidir.")
    private String lastName;

    @Min(value = 18, message = "Yaş 18 ile 110 arasında olmalıdır.")
    @Max(value = 110, message = "Yaş 18 ile 110 arasında olmalıdır.")
    @NotNull(message = "Yaş alanı boş olamaz")
    private Integer age;

    @Min(value = 1500, message = "En düşük maaş 1500 olmalıdır.")
    @NotNull(message = "Maaş alanı boş olamaz")
    private Integer salary;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+){5,20}$", message = "Şifre en az bir sayı ve bir harf içermelidir ve toplamda en az 5 en fazla 20 karakter içermelidir.")
    @NotEmpty(message = "Şifre alanı boş olamaz")
    private String password;

    @NotNull(message = "Role alanı boş olamaz")
    private RoleDTO role;

    @NotNull(message = "Departman alanı boş olamaz")
    private DepartmentDTO department;

    private List<ProjectDTO> projects;

    public EmployeeDTO() {
        projects = new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
