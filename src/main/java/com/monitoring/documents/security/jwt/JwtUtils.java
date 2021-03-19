package com.monitoring.documents.security.jwt;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${monitoringDocuments.app.jwtSecret}")
    private String jwtSecret;

    @Value("${monitoringDocuments.app.jwtExpirationMs}")
    private String jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try
        {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException exception) {
            logger.error("Semnatura jwt este invalida: {}", exception.getMessage());
        } catch(MalformedJwtException exception) {
            logger.error("Token-ul jwt este invalid: {}", exception.getMessage());
        } catch(ExpiredJwtException e) {
            logger.error("Token-ul jwt a expirat: {}", e.getMessage());
        } catch(UnsupportedJwtException e) {
            logger.error("Token-ul nu este suportat: {}", e.getMessage());
        } catch(IllegalArgumentException e) {
            logger.error("String-ul generat de jwt este gol: {}", e.getMessage());
        }

        return false;
    }
}
