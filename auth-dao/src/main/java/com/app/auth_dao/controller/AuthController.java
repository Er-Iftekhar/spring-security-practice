package com.app.auth_dao.controller;

import com.app.auth_dao.model.AuthRequest;
import com.app.auth_dao.model.AuthResponse;
import com.app.auth_dao.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/v0")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth")
    public String authorize(@RequestParam Map<String,String> params){
        AuthRequest request = new AuthRequest(params);
        AuthResponse response = authService.processRequest(request);
        String redirectUri = authService.getRedirect(request);
        return "redirect:" + redirectUri + "#" + response.getParams();
    }
}
