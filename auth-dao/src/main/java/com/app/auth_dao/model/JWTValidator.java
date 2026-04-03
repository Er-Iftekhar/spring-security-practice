package com.app.auth_dao.model;

import org.springframework.security.authentication.BadCredentialsException;

public interface JWTValidator {

    String validateToken(String token) throws BadCredentialsException;
}
