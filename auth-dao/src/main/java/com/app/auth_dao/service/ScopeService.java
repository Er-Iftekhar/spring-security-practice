package com.app.auth_dao.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScopeService {

    public List<String> validateScopes(
            List<String> requested,
            List<String> allowed
    ){
        return requested.stream()
                .filter(allowed::contains)
                .toList();
    }
}
