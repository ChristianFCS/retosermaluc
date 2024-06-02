package com.ccondori.retosermaluc;

import com.ccondori.retosermaluc.auth.filters.JWTAuthenticationFilter;
import com.ccondori.retosermaluc.auth.filters.JwtAuthorizationFilter;
import com.ccondori.retosermaluc.auth.services.JWTService;
import com.ccondori.retosermaluc.auth.services.impl.MyBatisUserDetailService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

import static com.ccondori.retosermaluc.auth.config.PathsAuth.AUTH;
import static com.ccondori.retosermaluc.auth.config.PathsAuth.LOGIN;
import static com.ccondori.retosermaluc.commons.config.PathsGlobal.*;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurtityConfig {

    private final JWTService jwtService;
    private final MyBatisUserDetailService detailService;

    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    public SpringSecurtityConfig(JWTService jwtService, MyBatisUserDetailService detailService, AuthenticationConfiguration authenticationConfiguration) {
        this.jwtService = jwtService;
        this.detailService = detailService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth.requestMatchers(
                ROOT_API + AUTH + VERSION + LOGIN).permitAll()
                        .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Agregar filtros de JWT para la autenticación y la autorización
        http.addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), jwtService), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    // Configuración de CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
