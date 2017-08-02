package com.oonurkuru.backend.dto;

import com.oonurkuru.backend.domains.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur Kuru on 1.8.2017.
 */
public class CustomUserDetails implements UserDetails, Serializable {

    private Integer id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails() {
        authorities = new ArrayList<>();
    }

    public CustomUserDetails(Employee employee) {
        this.authorities = new ArrayList<>();
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.authorities.add(new SimpleGrantedAuthority(employee.getRole().getRole()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
