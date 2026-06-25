package com.douglas.salessystem.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**")
                            .hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/customers/**"
                        ).hasAnyRole("ADMIN", "MANAGER", "SELLER")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/customers/**"
                        ).hasAnyRole("ADMIN", "MANAGER", "SELLER")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/customers/**"
                        ).hasAnyRole("ADMIN", "MANAGER", "SELLER")
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/categories/**",
                                "/api/v1/products/**",
                                "/api/v1/payment-methods/**"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/categories/**",
                                "/api/v1/products/**",
                                "/api/v1/payment-methods/**"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/categories/**",
                                "/api/v1/products/**",
                                "/api/v1/payment-methods/**"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/categories/**",
                                "/api/v1/products/**",
                                "/api/v1/payment-methods/**"
                        ).hasAnyRole("ADMIN", "MANAGER", "SELLER")
                        .requestMatchers(
                                "/api/v1/sales/**"
                        ).hasAnyRole("ADMIN", "MANAGER", "SELLER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
