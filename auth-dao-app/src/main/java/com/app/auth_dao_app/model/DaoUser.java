package com.app.auth_dao_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DaoUser {
    private String username;
    private String password;
    private List<String> roles;
}
