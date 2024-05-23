package com.example.microservice_small_square.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private final Long id;

    private final LocalDate date;



    private final Long idChef;

    List<DishQuantify> dishes;

    private final Long idClient;

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    private final Long idRestaurant;


    public void setDishes(List<DishQuantify> dishes) {
        this.dishes = dishes;
    }

    public Order(Long id, Long idClient, LocalDate date,
                 Long idChef, List<DishQuantify> dishes,
                 Long idRestaurant) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.idChef = idChef;
        this.dishes = dishes;
        this.idRestaurant = idRestaurant;
    }

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

    public List<DishQuantify> getDishes() {
        return dishes;
    }







}
