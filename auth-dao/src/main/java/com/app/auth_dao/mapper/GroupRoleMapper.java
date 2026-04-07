package com.app.auth_dao.mapper;

import com.app.auth_dao.model.ParsedAdGroup;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class GroupRoleMapper {

    private static final Map<String, String> ROLES = Map.of(
            "OFFICER", "OFFICER",
            "F1MANAGER", "F1MANAGER",
            "F2MANAGER", "F2MANAGER",
            "COORDSUB", "COORDSUB",
            "COORDHEAD", "COORDHEAD"
    );

    private GroupRoleMapper(){}

//    Set<String> roles = new LinkedHashSet<>();

    public static List<String> mapRoles(List<String> groups){
        Set<String> roles = new LinkedHashSet<>();
        for (String group: groups){
            AdGroupParser.parse(group)
                    .map(GroupRoleMapper::mapRole)
                    .filter(role -> role != null && !role.isBlank())
                    .ifPresent(roles::add);
        }
        return List.copyOf(roles);
    }

//    public static List<String> mapRoles(List<String> groups){
//        return groups.stream()
//                .map(GroupRoleMapper::extractRole)
//                .flatMap(Optional::stream)
//                .distinct()
//                .toList();
//    }

//   private static Optional<String> extractRole(
//           String group
//   ){
//        return ROLES.keySet()
//                .stream()
//                .filter(group::contains)
//                .findFirst()
//                .map(ROLES::get);
//   }

    private static String mapRole(ParsedAdGroup group){
        String roleToken = group.roleToken();
        if(roleToken == null || roleToken.isBlank()){
            return null;
        }
        return ROLES.get(group.roleToken());
    }
}
