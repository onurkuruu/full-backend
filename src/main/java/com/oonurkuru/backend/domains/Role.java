package com.oonurkuru.backend.domains;

import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.dto.RoleDTO;

import javax.persistence.*;

/**
 * Created by Onur Kuru on 21.7.2017.
 */

@Entity
@Table(name = "ROLE")
@Mapper(targetClass = RoleDTO.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Integer id;

    @Column(name = "ROLE")
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
