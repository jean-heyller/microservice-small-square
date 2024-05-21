package com.example.microservice_small_square.domain.model;

public class Dish {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private String category;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private Restaurant restaurant;
    private boolean active = true;

    public Dish(String name, double price, String description, String imageUrl, String category, Restaurant restaurant,Long id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.restaurant = restaurant;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public boolean isActive() {
        return active;
    }


}
