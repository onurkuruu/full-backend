package com.oonurkuru.backend.configurations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oonurkuru.backend.exceptions.models.ExceptionModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Onur Kuru on 2.8.2017.
 */
@Component
public class DeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ExceptionModel exceptionModel = new ExceptionModel(502, "Access Denied", "Yetkisiz Erişim.");

        ObjectMapper objectMapper = new ObjectMapper();

        httpServletResponse.setStatus(Response.Status.FORBIDDEN.getStatusCode());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(exceptionModel));
    }
}
