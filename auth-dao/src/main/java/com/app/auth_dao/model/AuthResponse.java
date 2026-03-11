package com.app.auth_dao.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthResponse {

    private final Map<String, String> map = new HashMap<>();

    public void set(String key, String value){
        map.put(key,value);
    }

    public String getParams(){
        return map.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
    }
}
