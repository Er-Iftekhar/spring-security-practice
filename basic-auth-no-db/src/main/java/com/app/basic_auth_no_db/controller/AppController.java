package com.app.basic_auth_no_db.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class AppController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/success")
    public String loginSuccess(){
        return "success";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }
}

