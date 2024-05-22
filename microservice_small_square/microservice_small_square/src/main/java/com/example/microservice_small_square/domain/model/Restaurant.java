package com.example.microservice_small_square.domain.model;




public class Restaurant {
    private final Long id;

    private String name;
    private String nit;


    private String address;
    private String phoneNumber;
    private String urlLogo;
    private Long ownerId;

    public Restaurant(Long id, String name, String nit, String address, String phoneNumber, String urlLogo, Long ownerId) {
        this.id = id;
        this.name = name;
        this.nit = nit;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.urlLogo = urlLogo;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getNit() {
        return nit;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getId() {
        return id;
    }





}
