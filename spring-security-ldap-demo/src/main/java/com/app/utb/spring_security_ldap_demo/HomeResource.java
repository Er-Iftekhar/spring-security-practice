package com.app.utb.spring_security_ldap_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @GetMapping("/")
    public String index(){
        return "Home page";
    }
}
