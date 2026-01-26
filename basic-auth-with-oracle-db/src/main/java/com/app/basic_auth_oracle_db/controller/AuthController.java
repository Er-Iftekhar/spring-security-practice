package com.app.basic_auth_oracle_db.controller;

import com.app.basic_auth_oracle_db.entity.Role;
import com.app.basic_auth_oracle_db.entity.User;
import com.app.basic_auth_oracle_db.repository.RoleRepository;
import com.app.basic_auth_oracle_db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("register/create")
    public String getRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("register")
    public String registerForm(User user, Model model){
        log.info("Inside registration");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(com.app.basic_auth_oracle_db.Role.USER.name())
                .orElseThrow();
        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);
        log.info("saved user: {}", savedUser);
        model.addAttribute("user", savedUser);
        return "register";
    }

    @GetMapping("home")
    public String home(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "home";
    }

    @GetMapping("admin")
    public String adminPage(){
        return "admin";
    }
}
