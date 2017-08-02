package com.oonurkuru.backend.configurations.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Onur Kuru on 1.8.2017.
 */

@Service
public class SecurityService {

    public Boolean hasProtectedAccess() {
        return (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
