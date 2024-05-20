package com.example.microservice_small_square.domain.model;




public class Restaurant {

    private String name;
    private String nit;
    private String address;
    private String phoneNumber;
    private String urlLogo;
    private String ownerId;

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

    public void setOwnerId(String ownerId) {
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

    public String getOwnerId() {
        return ownerId;
    }




}
