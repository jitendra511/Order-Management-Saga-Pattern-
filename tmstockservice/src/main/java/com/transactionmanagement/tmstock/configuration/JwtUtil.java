package com.transactionmanagement.tmstock.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Component
public class JwtUtil {
    private final SecretKey secretKey;
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return !isTokenExpired(claims);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid JWT Token: " + e.getMessage());
        }
    }
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
    public String extractEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }
    public String extractRoles(String token) {
        return extractClaims(token).get("authorities", String.class);
    }
}