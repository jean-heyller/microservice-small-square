package com.example.microservice_small_square.adapters.driven.driving.http.util;

public class AdapterConstans {

    private AdapterConstans() {
        throw new IllegalStateException("Utility class");
    }

    public enum field{
        NAME,
        DESCRIPTION,
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "`name` cannot be null";
    public static final String FIELD_NAME_SIZE_MESSAGE = "`name` cannot be greater than 50 characters";

    public static final String FIELD_NIT_NULL_MESSAGE = "`nit` cannot be null";
    public static final String FIELD_NIT_PATTERN_MESSAGE = "`nit` should be valid";

    public static final String FIELD_ADDRESS_NULL_MESSAGE = "`address` cannot be null";
    public static final String FIELD_ADDRESS_SIZE_MESSAGE = "`address` cannot be greater than 100 characters";

    public static final String FIELD_PHONE_NUMBER_NULL_MESSAGE = "`phone number` cannot be null";
    public static final String FIELD_PHONE_NUMBER_PATTERN_MESSAGE = "`phone number` should be valid";

    public static final String FIELD_URL_LOGO_NULL_MESSAGE = "`url logo` cannot be null";

    public static final String FIELD_OWNER_ID_NULL_MESSAGE = "`owner id` cannot be null";

    public static final String FIELD_NAME_PATTERN_MESSAGE = "`name` should be valid";



}
