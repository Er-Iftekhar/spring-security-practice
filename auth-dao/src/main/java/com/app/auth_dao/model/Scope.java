package com.app.auth_dao.model;

public enum Scope {

    OPENID("openid"),
    PROFILE("profile"),
    EMAIL("email"),

    OFFICER("officer"),
    COORDINATOR("coordinator"),
    ADMIN("admin");

    private final String value;

    Scope(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
