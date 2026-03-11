package com.app.auth_dao.service;

import java.util.List;

public interface JWTService {
    String createAccessToken(String username, List<String> scope);
    int getExpiration();
}
