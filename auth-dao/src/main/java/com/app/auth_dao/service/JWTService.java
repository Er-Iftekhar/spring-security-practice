package com.app.auth_dao.service;

import com.app.auth_dao.model.JWTValidator;
import com.app.auth_dao.model.UserInfo;

import java.util.List;

public interface JWTService extends JWTValidator {
    String createIdToken(String username, List<String> scope, UserInfo userInfo);
    String createAccessToken(String username, List<String> scopes);
    int getExpiration();
}
