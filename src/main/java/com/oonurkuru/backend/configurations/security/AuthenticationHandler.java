package com.oonurkuru.backend.configurations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oonurkuru.backend.exceptions.models.ExceptionModel;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Onur Kuru on 23.7.2017.
 */

/**
 * Spring Security Yetkisiz Erişim olduğunda çalıştırılacak sınıf
 */
@Component
public class AuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ExceptionModel exceptionModel = new ExceptionModel(501, "UNAUTHORIZED", "Geçersiz token.");

        ObjectMapper objectMapper = new ObjectMapper();

        httpServletResponse.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(exceptionModel));
    }
}