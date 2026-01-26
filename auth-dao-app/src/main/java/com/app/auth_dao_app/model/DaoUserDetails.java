package com.app.auth_dao_app.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class DaoUserDetails extends User {

    public DaoUserDetails(DaoUser daoUser){
        super(
                daoUser.getUsername(),
                daoUser.getPassword(),
                daoUser.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
