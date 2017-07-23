package com.oonurkuru.backend.dto;

import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.domains.Role;

import java.io.Serializable;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

@Mapper(targetClass = Role.class)
public class RoleDTO implements Serializable {

    private Integer id;

    private String role;

    public RoleDTO() {
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
