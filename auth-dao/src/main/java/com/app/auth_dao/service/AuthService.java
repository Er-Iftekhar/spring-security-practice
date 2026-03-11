package com.app.auth_dao.service;

import com.app.auth_dao.model.AuthRequest;
import com.app.auth_dao.model.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthResponse processRequest(AuthRequest request){
        String responseType = request.get("response_type");

        if(responseType == null){
            throw new RuntimeException("Missing response_type");
        }

        if(responseType.equals("token")){
            return implicitFlow(request);
        }

        throw new RuntimeException("Unsupported response_type");
    }

    public String getRedirect(AuthRequest request){
        String redirectUri = request.get("redirect_uri");

        if(redirectUri == null){
            throw new RuntimeException("redirect_uri missing");
        }

        return redirectUri;
    }

    private AuthResponse implicitFlow(AuthRequest request){
        AuthResponse response = new AuthResponse();
        response.set("token_type", "Bearer");
        response.set("access_token", "dummy-token");
        response.set("expires_in", "3600");

        return response;
    }
}
