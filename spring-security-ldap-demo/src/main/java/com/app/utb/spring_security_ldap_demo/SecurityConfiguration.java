package com.app.utb.spring_security_ldap_demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().fullyAuthenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
            LdapBindAuthenticationManagerFactory factory =
                    new LdapBindAuthenticationManagerFactory(contextSource);

            factory.setUserDnPatterns("uid={0},ou=people");
            factory.setPasswordEncoder(new LdapShaPasswordEncoder());
            factory.setPasswordAttribute("userPassword");

            return factory.createAuthenticationManager();
    }

    @Bean
    public BaseLdapPathContextSource contextSource() {
        DefaultSpringSecurityContextSource contextSource =
                new DefaultSpringSecurityContextSource("ldap://localhost:8389/dc=springframework,dc=org");
        return contextSource;
    }
}
