package com.app.auth_java_fe_client.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
@Configuration
public class TokenClientAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Value("${auth.server}")
    private String authServer;

    @Value("${client.id}")
    private String clienntId;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String redirectUrl = authServer +
                "?client_id=" + clienntId +
                "&response_type=token&scope=openid";

        log.info("redirection url: {}", redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}
