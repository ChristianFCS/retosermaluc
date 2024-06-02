package com.ccondori.retosermaluc.auth.services.impl;

import com.ccondori.retosermaluc.auth.model.UserDetailsCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ccondori.retosermaluc.auth.filters.SimpleGrantedAuthorityMixin;
import com.ccondori.retosermaluc.auth.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Service
public class JWTServicesImpl implements JWTService {

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final Long EXIPIRATION_DATE = 3600000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Override
    public String create(Authentication authentication) {
        String username = ((UserDetailsCustom) authentication.getPrincipal()).getUsername();

        Claims claims = Jwts.claims()
                .add("username", username)
                .build();


        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .signWith(SECRET_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXIPIRATION_DATE))
                .compact();
    }

    @Override
    public boolean validate(String token) {

        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(resolve(token)).getPayload();
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        return Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
