package com.example.microservice_small_square.adapters.driven.driving.http.util;

import com.example.microservice_small_square.adapters.driven.security.CustomerUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public Long getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomerUserDetail userDetails) {
            return userDetails.getId();
        }
        return null;
    }
}
