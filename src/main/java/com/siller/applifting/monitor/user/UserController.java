package com.siller.applifting.monitor.user;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi{

    @Override
    public UserDto createUser(UserRegistrationDto u, HttpServletResponse response) {
        response.addHeader("link","foo");
        return null;
    }

    @Override
    public void removeUser(String login) {

    }


}
