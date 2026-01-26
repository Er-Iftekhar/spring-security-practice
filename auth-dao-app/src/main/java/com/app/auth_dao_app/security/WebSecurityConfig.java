package com.app.auth_dao_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http
    ) throws Exception{
        http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/login", "/error").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults());
        return http.build();
    }
}
