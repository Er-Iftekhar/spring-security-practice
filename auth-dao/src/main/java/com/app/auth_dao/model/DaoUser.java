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
    private List<String> roles;
    private List<String> units;

    public DaoUser(String username,
                   String password,
                   String authorities,
                   List<String> roles,
                   List<String> units) {
        super(username, password, List.of(new SimpleGrantedAuthority(authorities)));
        this.roles = roles;
        this.units = units;
    }

    public DaoUser(DaoUser other){
        super(other.getUsername(), other.getPassword(), other.getAuthorities());
        this.firstName = other.getFirstName();
        this.lastName = other.getLastName();
        this.email = other.getEmail();
        this.phone = other.getPhone();
        this.roles = other.getRoles();
        this.units = other.getUnits();
    }
}
