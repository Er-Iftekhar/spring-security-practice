package com.app.auth_dao.mapper;

import com.app.auth_dao.model.ParsedAdGroup;

import java.util.Optional;

public final class AdGroupParser {
    private AdGroupParser(){}

    public static Optional<ParsedAdGroup> parse(String group){
        if(group == null || group.isBlank()){
            return Optional.empty();
        }

        String[] parts = group.split("-");
        if(parts.length < 5){
            return Optional.empty();
        }
        String prefix = parts[0];
        String productId = parts[1];
        String applicationAndUnit = parts[2];
        String roleToken = parts[3];
        String environment = parts.length > 4 ? parts[4] : null;

        int underscoreIndex = applicationAndUnit.indexOf('_');
        if (underscoreIndex < 0 || underscoreIndex == applicationAndUnit.length()-1) {
            return Optional.empty();
        }

        String application = applicationAndUnit.substring(0, underscoreIndex);
        String unitToken = applicationAndUnit.substring(underscoreIndex + 1);

        return Optional.of(new ParsedAdGroup(
                group,
                prefix,
                productId,
                application,
                unitToken,
                roleToken,
                environment
        ));
    }
}
