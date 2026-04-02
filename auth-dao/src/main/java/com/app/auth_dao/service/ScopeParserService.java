package com.app.auth_dao.service;

import java.util.Arrays;
import java.util.List;

public class ScopeParserService {
    public static List<String> parse(String scope){
        if(scope == null){
            return List.of();
        }

        return Arrays.stream(scope.split("\\s+"))
                .toList();
    }
}
