package com.siller.applifting.monitor.security;

import com.siller.applifting.monitor.user.service.User;
import com.siller.applifting.monitor.user.service.UserNotFound;
import com.siller.applifting.monitor.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    private final String authorizationSchemaName;

    private final UserService userService;

    public AuthenticationFilter(String authorizationSchemaName, UserService userService)  {
        this.authorizationSchemaName = authorizationSchemaName;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if( servletRequest instanceof HttpServletRequest){
            String authorizationHeader = ((HttpServletRequest) servletRequest).getHeader("Authorization");
            if(authorizationHeader != null ) {
                String[] authorizationHeaderParts = authorizationHeader.trim().replaceAll(" +", " ").split(" ");
                if (authorizationHeaderParts.length > 0 && authorizationHeaderParts[0].equals(authorizationSchemaName)) {
                    if (authorizationHeaderParts.length != 2) {
                        throw new BadCredentialsException("token value is missing");
                    }
                    Authentication token = CustomTokenAuthentication.fromToken(authorizationHeaderParts[1]);
                    SecurityContextHolder.getContext().setAuthentication(authenticate(token));
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication.getCredentials() == null){
            return null;
        }

        User user;
        try {
            user = userService.getUserByToken(((CustomTokenAuthentication) authentication).getCredentials());
        } catch (UserNotFound e){
            return null;
        }

        return user == null ? null : CustomTokenAuthentication.authenticatedFromId(user.getId());
    }
}
