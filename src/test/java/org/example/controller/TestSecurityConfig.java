package org.example.controller;

import org.example.Configuration.security.WebSecurityConfig;
import org.example.Configuration.security.auth.Http401UnauthorizedEntryPoint;
import org.example.Configuration.security.token.AccessTokenDecoder;
import org.example.Configuration.security.token.AccessTokenEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.mockito.Mockito.mock;
import static org.springframework.security.config.Customizer.withDefaults;

@TestConfiguration
@EnableWebSecurity
@Import(WebSecurityConfig.class)
public class TestSecurityConfig {
    @Bean
    public AccessTokenDecoder accessTokenDecoder() {
        return mock(AccessTokenDecoder.class);
    }

    @Bean
    public AccessTokenEncoder accessTokenEncoder() {
        return mock(AccessTokenEncoder.class);
    }

    @Bean
    public Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint() {
        return mock(Http401UnauthorizedEntryPoint.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(http401UnauthorizedEntryPoint())
                );

        return http.build();
    }
}