package com.siller.applifting.monitor.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;

public interface UsersApi {

    public static final String USER_PATH_PREFIX="/users";
    @PostMapping(USER_PATH_PREFIX)
    UserDto createUser(UserRegistrationDto u, HttpServletRequest request, HttpServletResponse response);

}
