package com.example.microservice_small_square.domain.model;

public class DishQuantify {
    private Long idDish;

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getIdDish() {
        return idDish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    private Integer quantity;
}
