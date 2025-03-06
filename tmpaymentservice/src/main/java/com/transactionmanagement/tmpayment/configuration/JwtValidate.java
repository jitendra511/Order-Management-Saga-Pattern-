package com.transactionmanagement.tmpayment.configuration;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
@Component
public class JwtValidate extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    public JwtValidate(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            try {
                Claims claims = jwtUtil.extractClaims(jwt);
                String email = claims.get("email", String.class);
                String authorities = claims.get("authorities", String.class);
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, jwt, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Extracted Roles: " + authorities);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
