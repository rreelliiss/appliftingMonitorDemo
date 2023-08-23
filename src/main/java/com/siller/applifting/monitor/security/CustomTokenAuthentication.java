package com.siller.applifting.monitor.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class CustomTokenAuthentication implements Authentication {

    private final String token;

    private boolean isAuthenticated;

    private final UUID id;

    public static CustomTokenAuthentication fromToken(String token){
        return new CustomTokenAuthentication(token, null);
    }

    public static CustomTokenAuthentication authenticatedFromId(UUID id){
        CustomTokenAuthentication customTokenAuthentication = new CustomTokenAuthentication(null, id);
        customTokenAuthentication.setAuthenticated(true);
        return customTokenAuthentication;
    }

    private CustomTokenAuthentication(String token, UUID id) {
        this.token = token;
        this.id = id;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UUID getPrincipal() {
        return id;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
