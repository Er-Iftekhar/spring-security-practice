package com.app.auth_dao.mapper;

import com.app.auth_dao.model.ParsedAdGroup;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GroupUnitMapper {
    private static final Map<String, String> UNITS = Map.of(
            "MP_XBLONDON", "CB UK",
            "MP_XBOSLO", "CB NORWAY",
            "MP_ALL", "ALL"
    );

    public static List<String> mapUnits(List<String> groups){

        Set<String> units = new LinkedHashSet<>();
        for(String group: groups){
            AdGroupParser.parse(group)
                    .map(GroupUnitMapper::mapUnit)
                    .filter(unit -> unit != null && !unit.isBlank())
                    .ifPresent(units::add);
        }
        return List.copyOf(units);
    }

//    public static List<String> mapUnits(
//            List<String> groups
//    ){
//        return groups.stream()
//                .map(GroupUnitMapper::extractUnit)
//                .flatMap(Optional::stream)
//                .distinct()
//                .toList();
//    }

    public static String mapUnit(ParsedAdGroup group){
        String unitToken = group.unitToken();
        if (unitToken == null || unitToken.isBlank()){
            return null;
        }
        return switch (unitToken) {
            case "XBLONDON" -> "CB UK";
            case "XBOSLO" -> "CB NORWAY";
            case "ALL" -> "ALL";
            default -> null;
        };
    }


    private static Optional<String> extractUnit(
            String group
    ){
        return UNITS.keySet()
                .stream()
                .filter(group::contains)
                .findFirst()
                .map(UNITS::get);
    }
}
