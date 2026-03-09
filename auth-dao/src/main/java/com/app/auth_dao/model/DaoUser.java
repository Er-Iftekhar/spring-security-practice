package com.app.auth_dao.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@Setter
public class DaoUser extends User implements UserInfo{

    private String firstName;
    private String lastName;
    private String email;
    private String phone;


    public DaoUser(String username, String password, String authorities) {
        super(username, password, List.of(new SimpleGrantedAuthority(authorities)));
    }

    public DaoUser(DaoUser other){
        super(other.getUsername(), other.getPassword(), other.getAuthorities());
        this.firstName = other.getFirstName();
        this.lastName = other.getLastName();
        this.email = other.getEmail();
        this.phone = other.getPhone();
    }
}
