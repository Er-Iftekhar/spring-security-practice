package com.app.auth_dao.service;

import com.app.auth_dao.model.AuthRequest;
import com.app.auth_dao.model.AuthResponse;
import com.app.auth_dao.model.Authority;
import com.app.auth_dao.model.Client;
import com.app.auth_dao.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientService clientService;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthResponse processRequest(AuthRequest request){
        String responseType = request.get("response_type");

        if(responseType == null || responseType.isBlank()){
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

        if(clientId == null || clientId.isBlank()){
            throw new RuntimeException("redirect_uri missing");
        }

        Client client = clientService
                .findById(clientId)
                .orElseThrow(() -> new RuntimeException("Unauthorized client: " + clientId));

        String redirectUri = request.get("redirect_uri");
        if(redirectUri != null && !client.redirectUri().equals(redirectUri)){
            throw new RuntimeException("INvalid redirect URI");
        }
        return client.redirectUri();
    }

    private AuthResponse implicitFlow(AuthRequest request){
        AuthResponse response = new AuthResponse();
        response.set("token_type", "Bearer");
        response.set("access_token", createAccessToken());
        response.set("expires_in", String.valueOf(jwtService.getExpiration()));

        scope(request, response);

        return response;
    }

    private void scope(AuthRequest request, AuthResponse response){
        String scope = request.get("scope");

        if(scope == null){
            return;
        }
        if(Arrays.asList(scope.split("\\s")).contains("openid")){
            openId(response);
        }
    }

    private void openId(AuthResponse response){
        try {
            response.set("id_token", createIdToken());
        } catch (Exception e) {
            log.error("Cannot get user info: {}", e.getMessage());
        }
    }

    private String createAccessToken(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<String> scope = Authority.mapAuthorities(authentication);
        return jwtService.createAccessToken(userName, scope);
    }

    private String createIdToken(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<String> scope = Authority.mapAuthorities(authentication);
        UserInfo userInfo = (UserInfo) userDetailsService.loadUserByUsername(userName);
        return jwtService.createIdToken(userName, scope, userInfo);
    }
}
