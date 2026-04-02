package com.app.auth_dao.model;

import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Authorities {

    COORINATOR(
            "COORDINATOR",
            Scope.COORDINATOR
    ),
    OFFICER(
            "OFFICER",
            Scope.OFFICER
    );
    private final String role;
    private final Scope scope;

    Authorities(String role, Scope scope){
        this.role = role;
        this.scope = scope;
    }

    public Scope scope(){
        return scope;
    }

    public static List<String> mapAuthorities(Authentication authentication){
        return authentication.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .map(role -> Arrays.stream(values())
                .filter(x -> x.role.equals(role))
                                .findFirst())
                .flatMap(Optional::stream)
                .map(x -> x.scope.value())
                .toList();
    }
}
