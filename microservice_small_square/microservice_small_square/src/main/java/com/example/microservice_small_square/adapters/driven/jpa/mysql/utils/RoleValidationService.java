package com.example.microservice_small_square.adapters.driven.jpa.mysql.utils;
import com.example.microservice_small_square.adapters.driven.client.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;


@Service
public class RoleValidationService {
    private final UserClient userClient;

    public RoleValidationService(UserClient userClient) {
        this.userClient = userClient;
    }

    public boolean validateUserRole(Long userId, String role) {
        String jsonRolName = userClient.getUserRoles(userId);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonRolName);
            String rolName = jsonNode.get("name").asText();

            System.out.println("El rol del usuario es: " + rolName);
            return role.equals(rolName);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el JSON", e);
        }
    }
}