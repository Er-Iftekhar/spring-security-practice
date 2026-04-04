package com.app.auth_dao.model;

import java.util.List;

public interface UserInfo {
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhone();
    List<String> getRoles();
    List<String> getUnits();
}
