package com.app.auth_java_fe_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("message", "Hello from the Frontend");
        return "home";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("message", "Hello from the dashboard");
        return "dashboard";
    }

    @GetMapping("/public")
    public String publicAccess(Model model){
        model.addAttribute("message", "Hello from the dashboard");
        return "public";
    }
}
