package com.app.auth_dao.service;

import com.app.auth_dao.model.Authority;
import com.app.auth_dao.model.DaoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class DaoUserDetailsService implements UserDetailsService {

    public static final String ENCRYPTED_PASSWORD = "$2a$10$JaqX1eHfBCBsN8pjf8yDNOSIhNmTummip/AI39naa4/GMrsIR5IHC";

    public DaoUserDetailsService(){
        initUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            DaoUser user = findByUsername(username.split("@")[0]);
            log.debug("DAO username: {} authorities: {}", user.getUsername(), user.getAuthorities());
            return new DaoUser(user);
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException(username);
        }
    }

    private DaoUser findByUsername(String userName){
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(userName)).findAny().orElseThrow();
    }

    List<DaoUser> users = new ArrayList<>();
    private void initUsers(){
        users.add(new DaoUser("officer1", ENCRYPTED_PASSWORD, Authority.OFFICER_AUTHORITY));
        users.add(new DaoUser("officer2", ENCRYPTED_PASSWORD, Authority.OFFICER_AUTHORITY));

        users.add(new DaoUser("coordinator1", ENCRYPTED_PASSWORD, Authority.COORDINATOR_AUTHORITY));
        users.add(new DaoUser("coordinator2", ENCRYPTED_PASSWORD, Authority.COORDINATOR_AUTHORITY));
    }
}
