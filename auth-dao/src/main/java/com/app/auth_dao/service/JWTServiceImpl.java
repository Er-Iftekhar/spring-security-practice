package com.app.auth_dao.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
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
    public String createAccessToken(String username, List<String> scope) {
        return tokenBuilder()
                .withSubject(username)
                .withClaim("scope", scope)
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
}
