package com.oonurkuru.backend.utils;

import com.oonurkuru.backend.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Onur Kuru on 1.8.2017.
 */
@Component
public class TokenUtils {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;

    public Object getValueFromToken(String token, String key) {

        Object value;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            value = claims.get(key);
        } catch (Exception e) {
            value = null;
        }

        return value;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        claims.put(Constants.TOKEN_USERNAME, customUserDetails.getUsername());
        claims.put(Constants.TOKEN_CREATED_DATE, this.generateCurrentDate());
        claims.put(Constants.TOKEN_PASSWORD, customUserDetails.getPassword());

        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException ex) {
            logger.warn(ex.getMessage());
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        final String firstName = (String) this.getValueFromToken(token, Constants.TOKEN_USERNAME);
        final String password = (String) this.getValueFromToken(token, Constants.TOKEN_PASSWORD);
//        final Date createdDate = (Date) this.getValueFromToken(token, Constants.TOKEN_CREATED_DATE);
//        final Date expirationDate = this.getExpirationDateFromToken(token);

        return (firstName.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword()) && !(this.isTokenExpired(token)));
    }
}
