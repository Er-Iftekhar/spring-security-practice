package com.app.auth_dao.model;

public record Client(
        String id,
        String secret,
        String redirectUri
) {
}
