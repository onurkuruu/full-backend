package com.oonurkuru.backend.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.ws.rs.HttpMethod;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Spring Security ayarları
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationHandler unauthorizedHandler;

    private final UserDetailsService userDetailsService;

    private final DeniedHandler accessDeniedHandler;

//    private final AuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfig(AuthenticationEntryPoint unauthorizedHandler, UserDetailsService userDetailsService, AccessDeniedHandler accessDeniedHandler) {
        this.unauthorizedHandler = (AuthenticationHandler) unauthorizedHandler;
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = (DeniedHandler) accessDeniedHandler;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)
                .accessDeniedHandler(this.accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/**/employees/{\\d+}").hasAnyRole("ADMIN", "USER")
                .anyRequest().hasAnyRole("ADMIN");

        httpSecurity
                .addFilterBefore(authenticationTokenBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFilter authenticationTokenBean() throws Exception {
        return new AuthenticationFilter(authenticationManagerBean());
    }

}
