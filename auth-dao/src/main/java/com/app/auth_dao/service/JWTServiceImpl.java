package com.app.auth_dao.service;

import com.app.auth_dao.model.UserInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class JWTServiceImpl implements JWTService {
    private final Algorithm algorithm;
    private static final int EXPIRATION = 12 * 3600;

    @Value("${app.host}")
    private String issuer;

    public JWTServiceImpl(@Value("${app.jwt.secret}") String secret){
        this.algorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public String createIdToken(String username, List<String> scope, UserInfo userInfo) {
        return tokenBuilder()
                .withSubject(username)
                .withClaim("scope", scope)
                .withClaim("first_name", userInfo.getFirstName())
                .withClaim("last_name", userInfo.getLastName())
                .withClaim("email", userInfo.getEmail())
                .withClaim("phone", userInfo.getPhone())
                .withClaim("roles", userInfo.getRoles())
                .withClaim("units", userInfo.getUnits())
                .sign(algorithm);
    }

    @Override
    public String createAccessToken(String username, List<String> scopes, UserInfo userInfo) {
        return tokenBuilder()
                .withSubject(username)
                .withClaim("scope", scopes)
                .withClaim("roles", userInfo.getRoles())
                .withClaim("units", userInfo.getUnits())
                .sign(algorithm);
    }

    @Override
    public int getExpiration() {
        return EXPIRATION;
    }

    private JWTCreator.Builder tokenBuilder(){
        LocalDateTime now = LocalDateTime.now().minusMinutes(1);

        Date issuedAt = Date.from(
                now.atZone(ZoneId.systemDefault()).toInstant());
        Date expiresAt = Date.from(
                now.plusSeconds(EXPIRATION)
                        .atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt);
    }

    @Override
    public String validateToken(String token) throws BadCredentialsException {
        DecodedJWT jwt = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);

        return jwt.getSubject();
    }
}
