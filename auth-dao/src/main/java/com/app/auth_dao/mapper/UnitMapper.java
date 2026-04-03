package com.app.auth_dao.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UnitMapper {
    private static final Map<String, String> UNITS = Map.of(
            "CBLONDON", "CB UK",
            "CBOSLO", "CB NORWAY"
    );

    public static List<String> mapUnits(
            List<String> groups
    ){
        return groups.stream()
                .map(UnitMapper::extractUnit)
                .flatMap(Optional::stream)
                .distinct()
                .toList();
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
