package com.example.microservice_small_square.adapters.driven.utils.services;
import com.example.microservice_small_square.adapters.driven.client.UserClient;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;


@Service
public class RoleValidationService {
    private final UserClient userClient;

    private static final  String MESSAGE = "Error validating the json response";

    private static final String MESSAGE_USER_NOT_FOUND = "the user ";

    public RoleValidationService(UserClient userClient) {
        this.userClient = userClient;
    }

    public boolean validateUserRole(Long userId, String role) {
        try {
            String jsonRolName = userClient.getUserRoles(userId);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonRolName);
            String attribute = "name";
            String rolName = jsonNode.get(attribute).asText();
            return rolName.equals(role);
        } catch (FeignException.BadRequest e) {
            throw new DataNotFoundException(MESSAGE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new DataNotFoundException(MESSAGE);
        }
    }
}