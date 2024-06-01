package com.ccondori.retosermaluc.auth.filters;


import com.ccondori.retosermaluc.auth.services.JWTService;
import com.ccondori.retosermaluc.auth.services.impl.JWTServicesImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,  JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTServicesImpl.HEADER_STRING);
        if (!requiereAuth(header)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = null;
        boolean isValidToken = jwtService.validate(header);
        if (isValidToken) {
            String username = jwtService.getUsername(header);
            authenticationToken = new UsernamePasswordAuthenticationToken(username,null, null);
        }

        /*autentica el usuario dentro del request*/
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    protected boolean requiereAuth(String header) {
        if (header == null || !header.startsWith(JWTServicesImpl.TOKEN_PREFIX)) {
            return false;
        }
        return true;
    }
}
