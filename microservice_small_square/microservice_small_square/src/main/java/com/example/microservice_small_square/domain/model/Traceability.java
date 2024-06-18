package com.example.microservice_small_square.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Traceability {
    private final Long id;


    private final String idClient;

    private  String emailClient;


    private final LocalDateTime date;

    private String statusBefore;



    private  String statusAfter;

    private final Long idEmployee;

    private  String emailEmployee;



    private  Long idOrder;

    private String status;


    public String getIdClient() {
        return idClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public Traceability(Long id,
                        String idClient,
                        String emailClient,
                        LocalDateTime date,
                        String statusBefore,
                        String statusAfter,
                        Long idEmployee,
                        String emailEmployee,
                        Long idOrder,
                        String status) {

        this.id = id;
        this.idClient = idClient;
        this.emailClient = emailClient;
        this.date = date;
        this.statusBefore = statusBefore;
        this.statusAfter = statusAfter;
        this.idEmployee = idEmployee;
        this.emailEmployee = emailEmployee;
        this.idOrder = idOrder;
        this.status = status;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }


    public void setStatusBefore(String statusBefore) {
        this.statusBefore = statusBefore;
    }

    public Long getId() {
        return id;
    }




    public LocalDateTime getDate() {
        return date;
    }

    public String getStatusBefore() {
        return statusBefore;
    }

    public String getStatusAfter() {
        return statusAfter;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public String getEmailEmployee() {
        return emailEmployee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusAfter(String statusAfter) {
        this.statusAfter = statusAfter;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
    }



}
