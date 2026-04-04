package com.app.auth_dao.service;

import com.app.auth_dao.mapper.GroupRoleMapper;
import com.app.auth_dao.mapper.GroupUnitMapper;
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
        DaoUser officer1 = new DaoUser("officer1", ENCRYPTED_PASSWORD, Authority.OFFICER_AUTHORITY);
        DaoUser coordinator1 = new DaoUser("coordinator1", ENCRYPTED_PASSWORD, Authority.COORDINATOR_HEAD_AUTHORITY_LONDON);
        DaoUser coordinator2 = new DaoUser("coordinator2", ENCRYPTED_PASSWORD, Authority.COORDINATOR_SUB_AUTHORITY_LONDON);

        coordinator1.setGroups(List.of(Authority.COORDINATOR_SUB_AUTHORITY_OSLO, Authority.COORDINATOR_SUB_AUTHORITY_LONDON));
        coordinator2.setGroups(List.of(Authority.COORDINATOR_HEAD_AUTHORITY_LONDON, Authority.COORDINATOR_SUB_AUTHORITY_LONDON));
        officer1.setGroups(List.of(Authority.OFFICER_AUTHORITY));

        users = List.of(officer1, coordinator2, coordinator1);

        users.forEach(u ->{
            String[] names = u.getUsername().split("-");

            u.setFirstName(names.length == 2 ? names[0] : "bcm");
            u.setLastName(names.length == 2 ? names[1] : names[0]);
            u.setEmail(u.getEmail() + "." + u.getLastName() + "@company.com");
            u.setPhone("420 2222 2222");

            List<String> groups = u.getGroups() == null ? List.of() : u.getGroups();
            u.setRoles(GroupRoleMapper.mapRoles(groups));
            u.setUnits(GroupUnitMapper.mapUnits(groups));
        });
    }
}
