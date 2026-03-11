package com.app.auth_dao.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@NoArgsConstructor
public class Authority {

    public static final String OFFICER_SCOPE = "OFFICER";
    public static final String OFFICER_AUTHORITY = "GD_CL_01SS19-ACCESS-OFFICER";

    public static final String F1_MANAGER_SCOPE = "F1_MANAGER";
    public static final String F1_MANAGER_AUTHORITY = "GD_CL_01SS19-ACCESS-F1_MANAGER";

    public static final String F2_MANAGER_SCOPE = "F2_MANAGER";
    public static final String F2_MANAGER_AUTHORITY = "GD_CL_01SS19-ACCESS-F2_MANAGER";

    public static final String COORDINATOR_SCOPE = "COORDINATOR";
    public static final String COORDINATOR_AUTHORITY = "GD_CL_01SS19-ACCESS-COORDINATOR";

    // TODO define scope for these roles
    public static final String D_SUPPORTER = "GD_CL_01SS19-ACCESS-D_SUPPORTER";
    public static final String B_SUPPORTER = "GD_CL_01SS19-ACCESS-B_SUPPORTER";
    public static final String AUDITOR = "GD_CL_01SS19-ACCESS-AUDITOR";

    private static final Map<String, String> authorityScopeMap = Map.of(
            OFFICER_AUTHORITY, OFFICER_SCOPE,
            COORDINATOR_AUTHORITY, COORDINATOR_SCOPE
            // TODO add scope map for further authorities
    );

    private static final Set<String> authorities = authorityScopeMap.keySet();

    public static String[] authoritiesAsArray(){
        return authorities.toArray(new String[0]);// The toArray(T[] a) method needs an array of the correct type to know what type of array to return.
    }

    public static List<String> mapAuthorities(Authentication authentication){
        return mapAuthorities(authentication.getAuthorities());
    }

    public static List<String> mapAuthorities(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(Authority.authorities::contains)
                .map(authorityScopeMap::get)
                .toList();
    }
}
