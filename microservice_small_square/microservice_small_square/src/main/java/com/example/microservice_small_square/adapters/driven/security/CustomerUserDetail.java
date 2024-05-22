package com.example.microservice_small_square.adapters.driven.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomerUserDetail extends User {

    private final Long id;

    public CustomerUserDetail(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
