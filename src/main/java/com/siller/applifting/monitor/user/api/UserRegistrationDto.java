package com.siller.applifting.monitor.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRegistrationDto {

    private String username;

    private String email;
}
