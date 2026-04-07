package com.app.auth_dao.model;

public record ParsedAdGroup(
        String raw,
        String prefix,
        String productId,
        String application,
        String unitToken,
        String roleToken,
        String environment
) {
}
