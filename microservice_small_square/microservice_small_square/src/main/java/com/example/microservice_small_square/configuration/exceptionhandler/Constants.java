package com.example.microservice_small_square.configuration.exceptionhandler;

public class Constants {
    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String MAX_CHAR_EXCEPTION_MESSAGE = "Field %s can not exceed %s characters";


    public static final String DATA_NOT_FOUND_EXCEPTION_MESSAGE = "does not exist";

    public static final String SIZE_MIN_EXCEPTION_MESSAGE = "The price must be greater than 0";

    public static final String PERMISSION_DENIED_EXCEPTION_MESSAGE = "r does not have permissions for this function";

    public static final String VALUE_ALREADY_EXISTS_EXCEPTION_MESSAGE = " indicated is already in use";

    public static final String PENDING_STATUS_EXCEPTION_MESSAGE = "There is already an order pending for this restaurant\"";

    public static final String NULL_FIELD_EXCEPTION_MESSAGE = "Field %s can not be null";

    public static final String ORDER_STATUS_EXCEPTION_MESSAGE = " is not in a valid state for this operation";

    public static final String NO_MESSAGES_FOUND_EXCEPTION_MESSAGE = "No messages found";



    public static final String CODE_NOT_VALIDATED_EXCEPTION_MESSAGE = "The code has not been validated";
}

