package com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions;

public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String message) {
        super(message);
    }
}
