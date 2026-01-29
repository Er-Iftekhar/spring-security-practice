package com.app.auth_ad_demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/")
    public String sayHello(){
        log.info("Authorities: {}",
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities()
                );
        return "Hello! Spring Boot is running";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Admin area";
    }
}
