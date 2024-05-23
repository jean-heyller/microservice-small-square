package com.example.microservice_small_square.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Long id;

    public Long getId() {
        return id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getIdChef() {
        return idChef;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    private Long idClient;

    public Order(Long id, Long idClient, LocalDate date, Long idChef, List<Dish> dishes) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.idChef = idChef;
        this.dishes = dishes;
    }

    private LocalDate date;

    private Long idChef;

    List<Dish> dishes;


}
