package com.siller.applifting.monitor.user;

import com.siller.applifting.monitor.user.api.UserDto;
import com.siller.applifting.monitor.user.api.UserRegistrationDto;
import com.siller.applifting.monitor.user.service.User;
import com.siller.applifting.monitor.user.service.UserRegistration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserRegistration toMonitoredEndpointRegistration(UserRegistrationDto userRegistrationDto);

    public abstract User toUser(UserRegistration userRegistration, String token);

    public abstract UserDto toUserDto(User user);

}
