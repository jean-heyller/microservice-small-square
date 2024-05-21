package com.example.microservice_small_square.configuration.exceptionhandler;

public class Constants {
    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String MAX_CHAR_EXCEPTION_MESSAGE = "Field %s can not exceed %s characters";

    public static final String VALUE_ALREADY_EXISTS_EXCEPTION = " you want to create already exists";

    public static final String DATA_NOT_FOUND_EXCEPTION_MESSAGE = "does not exist";

    public static final String PERMISSION_DENIED_CREATE_USER_EXCEPTION_MESSAGE = "Permission denied to create user";

    public static final String BAD_CREDENTIALS_EXCEPTION_MESSAGE = "User or password incorrect";

    public static final String SIZE_MIN_EXCEPTION_MESSAGE = "The price must be greater than 0";
}

