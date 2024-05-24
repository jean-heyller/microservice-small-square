package com.example.microservice_small_square.domain.model;

public class DishQuantify {
    private Long idDish;
    private Integer quantity;

    public Long getIdDish() {
        return idDish;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

    public DishQuantify(Long idDish, Integer quantity) {
        this.idDish = idDish;
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    public Integer getQuantity() {
        return quantity;
    }


}
