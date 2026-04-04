package com.app.auth_dao.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public final class Authority {

    public static final String OFFICER_SCOPE = "OFFICER";
    public static final String OFFICER_AUTHORITY = "XR_XL-0XXX1-MP_ALL-OFFICER-PRD";

    public static final String F1_MANAGER_SCOPE = "F1_MANAGER";
    public static final String F1_MANAGER_AUTHORITY_LONDON = "XR_XL-0XXX1-MP_XBLONDON-F1MANAGER-PRD";

    public static final String F2_MANAGER_SCOPE = "F2_MANAGER";
    public static final String F2_MANAGER_AUTHORITY_LONDON = "XR_XL-0XXX1-MP_XBLONDON-F2MANAGER-PRD";

    public static final String COORDINATOR_SCOPE = "COORDINATOR";
    public static final String COORDINATOR_SUB_AUTHORITY_LONDON = "XR_XL-0XXX1-MP_XBLONDON-COORDSUB-PRD";
    public static final String COORDINATOR_SUB_AUTHORITY_OSLO = "XR_XL-0XXX1-MP_XBOSLO-COORDSUB-PRD";
    public static final String COORDINATOR_HEAD_AUTHORITY_LONDON = "XR_XL-0XXX1-MP_XBLONDON-COORDHEAD-PRD";

    // TODO define scope for these roles
    public static final String D_SUPPORTER = "XR_XL-0XXX1-MP_XBLONDON-DSUPPORT-PRD";
    public static final String B_SUPPORTER = "XR_XL-0XXX1-MP_XBLONDON-BSUPPORT-PRD";
    public static final String AUDITOR = "XR_XL-0XXX1-MP_XBLONDON-AUDITOR-PRD";

    private Authority(){}

    private static final Map<String, String> authorityScopeMap = Map.of(
            OFFICER_AUTHORITY, OFFICER_SCOPE,
            COORDINATOR_SUB_AUTHORITY_LONDON, COORDINATOR_SCOPE,
            COORDINATOR_HEAD_AUTHORITY_LONDON, COORDINATOR_SCOPE,
            COORDINATOR_SUB_AUTHORITY_OSLO, COORDINATOR_SCOPE
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
