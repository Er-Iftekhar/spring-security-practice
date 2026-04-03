package com.app.auth_dao.model;

public record TokenRequest(
        String grant_type,
        String client_id,
        String client_secret,
        String username,
        String password
) {
}
