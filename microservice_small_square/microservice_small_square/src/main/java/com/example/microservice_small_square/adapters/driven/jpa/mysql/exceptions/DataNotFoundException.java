package com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
