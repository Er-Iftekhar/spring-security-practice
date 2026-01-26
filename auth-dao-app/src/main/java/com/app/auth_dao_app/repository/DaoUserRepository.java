package com.app.auth_dao_app.repository;

import com.app.auth_dao_app.model.DaoUser;

import java.util.ArrayList;
import java.util.List;

public class DaoUserRepository {
    public static final String ENCRYPTED_PASSWORD = "$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36z1QqY7FvJ8Z6zH3w5qK3C";
    private final List<DaoUser> users = new ArrayList<>();
    public static final String ROLE_OFFICER = "ROLE_OFFICER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public DaoUserRepository(){
        users.add(new DaoUser(
                "officer1",
                ENCRYPTED_PASSWORD,
                List.of(ROLE_OFFICER)
        ));

        users.add(
                new DaoUser(
                        "admin",
                        ENCRYPTED_PASSWORD,
                        List.of(ROLE_ADMIN)
                )
        );
    }

    public DaoUser findByUserName(String userName){
        return users.stream()
                .filter(u -> userName.equalsIgnoreCase(u.getUsername()))
                .findFirst()
                .orElseThrow();
    }
}
