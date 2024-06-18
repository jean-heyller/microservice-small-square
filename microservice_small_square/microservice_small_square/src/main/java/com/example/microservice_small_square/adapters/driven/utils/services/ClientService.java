package com.example.microservice_small_square.adapters.driven.utils.services;
import com.example.microservice_small_square.adapters.driven.client.SmsClient;
import com.example.microservice_small_square.adapters.driven.client.UserClient;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddSmsSenderRequest;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;


@Service
public class ClientService {
    private final UserClient userClient;

    private final SmsClient smsClient;

    private static final  String MESSAGE = "Error validating the json response";

    private static final String MESSAGE_USER_NOT_FOUND = "the user ";




    public ClientService(UserClient userClient, SmsClient smsClient) {
        this.userClient = userClient;
        this.smsClient = smsClient;
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

    public String getPhoneNumber(Long userId) {
        try {
            return userClient.getPhoneNumber(userId);
        } catch (FeignException.BadRequest e) {
            throw new DataNotFoundException(MESSAGE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new DataNotFoundException(MESSAGE);
        }
    }
    public String getEmail(Long userId) {
        try {
            return userClient.getEmail(userId);
        } catch (FeignException.BadRequest e) {
            throw new DataNotFoundException(MESSAGE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new DataNotFoundException(MESSAGE);
        }
    }

    public void sendSms(AddSmsSenderRequest addSmsSenderRequest) {
        try {
            smsClient.sendSms(addSmsSenderRequest);
        } catch (FeignException.BadRequest e) {
            throw new DataNotFoundException(MESSAGE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new DataNotFoundException(MESSAGE);
        }
    }

    public String getMessage(String number) {
        try {
            return smsClient.getMessage(number);
        } catch (FeignException.BadRequest e) {
            throw new DataNotFoundException(MESSAGE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new DataNotFoundException(MESSAGE);
        }
    }
}