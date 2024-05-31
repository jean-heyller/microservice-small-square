package com.example.microservice_small_square.adapters.driven.sms;
import com.example.microservice_small_square.domain.model.SmsSender;
import com.example.microservice_small_square.domain.spi.ISmsSenderService;
import com.twilio.Twilio;
import com.twilio.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmMService implements ISmsSenderService {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Value("${TWILIO_PHONE_NUMBER}")
    private String PHONE_NUMBER;


    private Map<String, String> messageMap = new HashMap<>();

    public void storeMessage(String phoneNumber, String messageCode) {
        messageMap.put(phoneNumber, messageCode);
    }

    public String retrieveMessage(String phoneNumber) {
        return messageMap.get(phoneNumber);
    }

    @Override
    public void sendSms(SmsSender smsSender) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            com.twilio.rest.api.v2010.account.Message.creator(
                    new com.twilio.type.PhoneNumber(smsSender.getTo()),
                    new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                    smsSender.getMessage()
            ).create();

            String phoneNumberWithoutPrefix = smsSender.getTo().substring(3);

            storeMessage(phoneNumberWithoutPrefix, smsSender.getMessage());
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
