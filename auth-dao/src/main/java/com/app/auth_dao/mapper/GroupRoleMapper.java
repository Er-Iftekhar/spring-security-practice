package com.app.auth_dao.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class GroupRoleMapper {

    private static final Map<String, String> ROLES = Map.of(
            "OFFICER", "OFFICER",
            "F1MANAGER", "F1MANAGER",
            "F2MANAGER", "F2MANAGER",
            "COORDSUB", "COORDSUB",
            "COORDHEAD", "COORDHEAD"
    );

    private GroupRoleMapper(){}

    public static List<String> mapRoles(List<String> groups){
        return groups.stream()
                .map(GroupRoleMapper::extractRole)
                .flatMap(Optional::stream)
                .distinct()
                .toList();
    }

   private static Optional<String> extractRole(
           String group
   ){
        return ROLES.keySet()
                .stream()
                .filter(group::contains)
                .findFirst()
                .map(ROLES::get);
   }
}
