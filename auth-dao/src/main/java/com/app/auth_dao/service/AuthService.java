package com.app.auth_dao.service;

import com.app.auth_dao.model.AuthRequest;
import com.app.auth_dao.model.AuthResponse;
import com.app.auth_dao.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientService clientService;

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
        response.set("access_token", "dummy-token");
        response.set("expires_in", "3600");

        return response;
    }
}
