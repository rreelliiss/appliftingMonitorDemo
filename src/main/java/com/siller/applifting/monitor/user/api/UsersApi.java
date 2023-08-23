package com.siller.applifting.monitor.user;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface UsersApi {

    @PostMapping("/users")
    UserDto createUser(UserRegistrationDto u, HttpServletResponse response);

    @DeleteMapping("/user/{login}")
    void removeUser(@PathVariable String login);

}
