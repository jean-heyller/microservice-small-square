package com.example.microservice_small_square.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private final Long id;

    private final LocalDate date;



    private final Long idChef;

    private List<DishQuantify> dishesQuantify;



    private List<Dish> dishes;

    public List<DishQuantify> getDishesQuantify() {
        return dishesQuantify;
    }

    public void setDishesQuantify(List<DishQuantify> dishesQuantify) {
        this.dishesQuantify = dishesQuantify;
    }

    private final Long idClient;

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    private final Long idRestaurant;




    public Order(Long id, Long idClient, LocalDate date,
                 Long idChef, List<DishQuantify> dishesQuantify,
                 Long idRestaurant) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.idChef = idChef;
        this.dishesQuantify = dishesQuantify;
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



}
