package com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions;

public class ValueAlreadyExitsException extends RuntimeException{
    public ValueAlreadyExitsException(String message) {
        super(message);
    }
}
