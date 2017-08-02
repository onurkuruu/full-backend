package com.oonurkuru.backend.services.Impl;

import com.oonurkuru.backend.dao.EmployeeDao;
import com.oonurkuru.backend.domains.Employee;
import com.oonurkuru.backend.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Onur Kuru on 1.8.2017.
 */
@Service(value = "userDetailsService")
public class CustomUserDetailService implements UserDetailsService {

    final private EmployeeDao employeeDao;

    @Autowired
    public CustomUserDetailService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeDao.findEmployeeByUsername(username);
        return new CustomUserDetails(employee);
    }
}
