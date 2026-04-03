package com.app.auth_dao.controller;

import com.app.auth_dao.model.TokenRequest;
import com.app.auth_dao.model.TokenResponse;
import com.app.auth_dao.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TokenController {
    private final AuthService authService;

    @PostMapping("/v0/token")
    @ResponseBody
    public TokenResponse token(
            @ModelAttribute TokenRequest request
            ){
        return authService.processTokenRequest(request);
    }
}
