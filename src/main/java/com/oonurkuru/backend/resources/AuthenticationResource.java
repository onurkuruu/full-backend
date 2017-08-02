package com.oonurkuru.backend.resources;

import com.oonurkuru.backend.dto.CustomUserDetails;
import com.oonurkuru.backend.exceptions.models.ExceptionModel;
import com.oonurkuru.backend.utils.TokenUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Onur Kuru on 1.8.2017.
 */

@Component
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationResource(TokenUtils tokenUtils, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @POST
    public Response authenticationRequest(CustomUserDetails customUserDetails) {

        CustomUserDetails userDetails = (CustomUserDetails) this.userDetailsService.loadUserByUsername(customUserDetails.getUsername());
        String token;

        if (userDetails == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (!passwordEncoder.matches(customUserDetails.getPassword(), userDetails.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            token = this.tokenUtils.generateToken(userDetails);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionModel(600, "Unhandled Error", "Token Oluşturulamadı.")).build();
        }

        return Response.ok().entity("{\"token\": \"" + token + "\"}").build();
    }

}
