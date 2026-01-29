package com.app.auth_ad_demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/").hasRole("GD_CL-014419")
                                .anyRequest().authenticated()
                ).formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                ).logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            BaseLdapPathContextSource contextSource
    ){
        BindAuthenticator authenticator = new BindAuthenticator(contextSource);
        authenticator.setUserSearch(new FilterBasedLdapUserSearch(
                "CN=Users",
                "(sAMAccountName={0})",
                contextSource
        ));

        // Find Groups the user belongs to
        DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(
                contextSource,
                "OU=APP_ROLES"
        );
        authoritiesPopulator.setGroupSearchFilter("(member={0})");
        authoritiesPopulator.setGroupRoleAttribute("cn");
        authoritiesPopulator.setRolePrefix("ROLE_");
        authoritiesPopulator.setSearchSubtree(true);
        return new ProviderManager(
                new LdapAuthenticationProvider(authenticator, authoritiesPopulator)
        );
    }
}
