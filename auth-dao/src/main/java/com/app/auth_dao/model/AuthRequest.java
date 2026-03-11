package com.app.auth_dao.model;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class AuthRequest {

    private final Map<String, String> map;

    public String get(String key){
        return map.get(key);
    }
}
