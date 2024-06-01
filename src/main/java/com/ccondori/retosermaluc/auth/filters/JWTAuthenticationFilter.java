package com.ccondori.retosermaluc.auth.filters;

import com.ccondori.retosermaluc.commons.models.ResponseApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ccondori.retosermaluc.auth.model.Usuario;
import com.ccondori.retosermaluc.auth.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ccondori.retosermaluc.auth.config.PathsAuth.AUTH;
import static com.ccondori.retosermaluc.auth.config.PathsAuth.LOGIN;
import static com.ccondori.retosermaluc.commons.config.PathsGlobal.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //generando clave secreta dinamicamente


    private AuthenticationManager authenticationManage;
    private JWTService jwtService;



    public JWTAuthenticationFilter(AuthenticationManager authenticationManage, JWTService jwtService) {
        this.authenticationManage = authenticationManage;
        this.jwtService = jwtService;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(ROOT_API + AUTH + VERSION + LOGIN, "POST"));
    }

    /*autentiacion que no necesita un formulario*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = this.obtainUsername(request);
        username = username != null ? username.trim() : null;
        String password = "";
        if (username == null) {
            Usuario usuario;
            try {
                usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
                username = usuario.getUsername().trim();
                password = usuario.getPassword().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManage.authenticate(authenticationToken);
    }

    /*se ejecuta cuando el logeo fue correcto*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = jwtService.create(authResult);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);

        ResponseApi<Map<String, Object>> resultado = ResponseApi.build(body);

        response.getWriter().write(new ObjectMapper().writeValueAsString(resultado));
        response.setStatus(200);
        response.setContentType("application/json");


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseApi<?> responseApi = new ResponseApi<>();
        responseApi.setStatus(200);
        responseApi.setEstado(false);
        responseApi.setMensaje("El usuario y la contraseña no coinciden, por favor verifique los datos ingresados.");
        responseApi.setPayload(null);

        response.setStatus(200);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseApi));
        response.setContentType("application/json");
    }


}
