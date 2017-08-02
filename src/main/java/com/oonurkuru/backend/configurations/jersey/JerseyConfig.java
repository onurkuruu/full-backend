package com.oonurkuru.backend.configurations.jersey;

import com.oonurkuru.backend.configurations.validation.ApplicationExceptionMapper;
import com.oonurkuru.backend.configurations.validation.ValidationExceptionMapper;
import com.oonurkuru.backend.resources.AuthenticationResource;
import com.oonurkuru.backend.resources.DepartmentResource;
import com.oonurkuru.backend.resources.EmployeeResource;
import com.oonurkuru.backend.resources.ProjectResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Jersey için başlangıç ayarları
 */
@Configuration
@ApplicationPath("/api/v1")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(EmployeeResource.class);
        register(AuthenticationResource.class);
        register(DepartmentResource.class);
        register(ProjectResource.class);
        register(ApplicationExceptionMapper.class);
        register(ValidationExceptionMapper.class);
    }
}
