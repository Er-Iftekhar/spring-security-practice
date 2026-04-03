package com.app.auth_dao.model;

public record TokenResponse(
        String token_type,
        String access_token,
        int expires_in
) {
}
