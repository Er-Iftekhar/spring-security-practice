package com.app.auth_dao.service;

import com.app.auth_dao.model.Client;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private List<Client> clients;

    @PostConstruct
    public void initClients(){
        clients = List.of(
                new Client("bcm-portal", null, "http://localhost:3000"),
                new Client("bcm-admin", null, "http://localhost:3001"),
                new Client("bia", null, "http://localhost:3002"));
    }

    public Optional<Client> findById(String id){
        return clients.stream()
                .filter(client -> client.id().equals(id))
                .findAny();
    }
}
