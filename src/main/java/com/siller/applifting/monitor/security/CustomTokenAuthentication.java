package com.siller.applifting.monitor.endpointMonitoring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;


public class CustomTokenAuthentication implements Authentication {

    private final String token;

    private boolean isAuthenticated;

    private final String userName;

    public static CustomTokenAuthentication fromToken(String token){
        return new CustomTokenAuthentication(token, null);
    }

    public static CustomTokenAuthentication fromUsername(String username){
        return new CustomTokenAuthentication(null, username);
    }

    private CustomTokenAuthentication(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes instead");
        this.isAuthenticated = false;
    }

    @Override
    public String getName() {
        return null;
    }
}
