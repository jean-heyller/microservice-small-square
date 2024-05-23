package com.example.microservice_small_square.adapters.driven.jpa.mysql.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "dish")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 90)
    private String description;

    @Column
    private Double price;

    @Column
    private String imageUrl;

    @Column
    private String category;

    @Column
    private Boolean isActived = true;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private RestaurantEntity restaurant;
}
