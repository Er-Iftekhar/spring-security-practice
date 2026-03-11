package com.app.auth_dao.service;

import com.app.auth_dao.model.AuthRequest;
import com.app.auth_dao.model.AuthResponse;
import com.app.auth_dao.model.Authority;
import com.app.auth_dao.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientService clientService;
    private final JWTService jwtService;

    public AuthResponse processRequest(AuthRequest request){
        String responseType = request.get("response_type");

        if(responseType == null){
            throw new RuntimeException("Missing response_type");
        }

        return switch (responseType) {
            case "token" -> implicitFlow(request);
            case "code" -> throw new RuntimeException("code flow not implemented");
            default -> throw new RuntimeException("Invalid response_type");
        };
    }

    public String getRedirect(AuthRequest request){
        String clientId = request.get("client_id");

        if(clientId == null){
            throw new RuntimeException("redirect_uri missing");
        }

        Client client = clientService
                .findById(clientId)
                .orElseThrow(() -> new RuntimeException("Unauthorized client: " + clientId));

        String redirectUri = request.get("redirect_uri");
        if(redirectUri != null && !client.redirectUri().equals(redirectUri)){
            throw new RuntimeException("INvalid redirect URI");
        }
        return redirectUri;
    }

    private AuthResponse implicitFlow(AuthRequest request){
        AuthResponse response = new AuthResponse();
        response.set("token_type", "Bearer");
        response.set("access_token", createAccessToken());
        response.set("expires_in", String.valueOf(jwtService.getExpiration()));

        return response;
    }

    private String createAccessToken(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<String> scope = Authority.mapAuthorities(authentication);
        return jwtService.createAccessToken(userName, scope);
    }
}
