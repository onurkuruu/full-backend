package com.oonurkuru.backend.configurations.app;

import com.oonurkuru.backend.domains.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Spring boot başlatma ayarları
 */
@SpringBootApplication(scanBasePackages = "com.oonurkuru.backend")
@EntityScan(basePackageClasses = Employee.class)
@EnableJpaRepositories(basePackages = "com.oonurkuru.backend.dao")
public class FullBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullBackendApplication.class, args);
    }
}
