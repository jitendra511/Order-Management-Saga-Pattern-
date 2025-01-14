package com.transactionmanagement.tmuser.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtToken {
    public static final String SECRET_KEY="dsfdgthtyjyujtyhrtgvgrfvsvdrfvdbgfnghnthyngbfgbvdfvscvsdcvsdvfdbvgfbngfnhn";
    public static final String JWT_HEADER="Authorization";

    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Validate JWT Token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true; // Valid token
        } catch (Exception e) {
            return false; // Invalid token
        }
    }

    // Extract Claims from Token
    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    // Check if Token is Expired
    public static boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Extract Email from Token
    public static String extractEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    // Extract Roles from Token
    public static String extractRoles(String token) {
        return extractClaims(token).get("authorities", String.class);
    }
}
