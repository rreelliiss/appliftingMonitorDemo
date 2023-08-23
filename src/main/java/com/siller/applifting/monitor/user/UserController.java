package com.siller.applifting.monitor.user;


import com.siller.applifting.monitor.user.api.UserDto;
import com.siller.applifting.monitor.user.api.UserRegistrationDto;
import com.siller.applifting.monitor.user.api.UsersApi;
import com.siller.applifting.monitor.user.service.User;
import com.siller.applifting.monitor.user.service.UserRegistration;
import com.siller.applifting.monitor.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {

    private final UserService service;
    private final UserMapper mapper;

    UserController(
            @Autowired UserService userService,
            @Autowired UserMapper mapper){
        service = userService;
        this.mapper = mapper;
    }


    @Override
    public UserDto createUser(UserRegistrationDto userRegistrationDto, HttpServletRequest request, HttpServletResponse response) {
        UserRegistration userRegistration = mapper.toMonitoredEndpointRegistration(userRegistrationDto);
        User user = service.createUser(userRegistration);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return mapper.toUserDto(user);
    }



}
