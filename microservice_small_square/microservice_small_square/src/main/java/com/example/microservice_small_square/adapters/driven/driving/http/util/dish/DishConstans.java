package com.example.microservice_small_square.adapters.driven.driving.http.util.dish;

public class DishConstans {
    private DishConstans() {
        throw new IllegalStateException("Utility class");
    }

    public enum field{
        NAME,
        DESCRIPTION,
    }

    public static final String FIELD_DISH_ID_NULL_MESSAGE = "`dish id` cannot be null";

    public static final String FIELD_DISHES_NULL_MESSAGE = "`dishes` cannot be null";

}
