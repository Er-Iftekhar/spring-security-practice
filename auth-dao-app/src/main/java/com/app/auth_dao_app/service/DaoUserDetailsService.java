package com.app.auth_dao_app.service;

import com.app.auth_dao_app.model.DaoUser;
import com.app.auth_dao_app.model.DaoUserDetails;
import com.app.auth_dao_app.repository.DaoUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DaoUserDetailsService implements UserDetailsService {
    private final DaoUserRepository repository = new DaoUserRepository();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            DaoUser user = repository.findByUserName(username);
            return new DaoUserDetails(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException(username);
        }
    }
}
