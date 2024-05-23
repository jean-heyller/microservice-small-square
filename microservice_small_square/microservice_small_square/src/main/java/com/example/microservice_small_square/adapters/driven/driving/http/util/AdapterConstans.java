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

    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "`description` cannot be null";

    public static final String FIELD_DESCRIPTION_SIZE_MESSAGE = "`description` cannot be greater than 90 characters";

    public static final String FIELD_PRICE_NULL_MESSAGE = "`price` cannot be null";

    public static final String FIELD_PRICE_MIN_MESSAGE = "`price` should be greater than 0";

    public static final String FIELD_IMAGE_URL_NULL_MESSAGE = "`image url` cannot be null";

    public static final String FIELD_CATEGORY_NULL_MESSAGE = "`category` cannot be null";

    public static final String FIELD_RESTAURANT_ID_NULL_MESSAGE = "`restaurant id` cannot be null";

    public static final String FIELD_QUANTITY_SIZE_MESSAGE = "`quantity` should be greater than 0";

    public static final String FIELD_DISH_ID_NULL_MESSAGE = "`dish id` cannot be null";

    public static final String FIELD_DATE_FUTURE_MESSAGE = "`date` should be future or present";

    public static final String FIELD_CHEF_ID_NULL_MESSAGE = "`chef id` cannot be null";

    public static final String FIELD_DISHES_NULL_MESSAGE = "`dishes` cannot be null";

    public static final String FIELD_CLIENT_ID_NULL_MESSAGE = "`client id` cannot be null";

  public static final String FIELD_QUANTITY_NULL_MESSAGE = "'quantify cannot be null";








}
