package com.siller.applifting.monitor.endpointMonitoring.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    private String authorizationSchemeName;

    public void setAuthorizationSchemeName(@Value("${security.http.authorization.schemeName}") String authenticationSchemeName) {
        this.authorizationSchemeName = authorizationSchemeName;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if( servletRequest instanceof HttpServletRequest){
            String authorizationHeader = ((HttpServletRequest) servletRequest).getHeader("Authorization");
            String [] authorizationHeaderParts = authorizationHeader.trim().replaceAll(" +", " ").split(" ");
            if (authorizationHeaderParts.length > 0 && authorizationHeaderParts[0].equals(authorizationSchemeName)){
                if(authorizationHeaderParts.length != 2) {
                    //TODO return err
                }
                Authentication token =  CustomTokenAuthentication.fromToken(authorizationHeaderParts[1]);
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
