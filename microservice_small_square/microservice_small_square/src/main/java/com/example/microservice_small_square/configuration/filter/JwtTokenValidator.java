package com.example.microservice_small_square.configuration.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.microservice_small_square.adapters.driven.security.CustomerUserDetail;
import com.example.microservice_small_square.adapters.driven.utils.JwtUtils;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null){

            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            String username = jwtUtils.getUserNameFromToken(decodedJWT);
            String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();
            Long id = jwtUtils.getSpecificClaim(decodedJWT, "id").asLong();
            Collection<? extends GrantedAuthority> autoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            SecurityContext context = SecurityContextHolder.getContext();
            CustomerUserDetail principal = new CustomerUserDetail(id, username, "", autoritiesList);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, autoritiesList);

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        }

        filterChain.doFilter(request, response);
    }

}
