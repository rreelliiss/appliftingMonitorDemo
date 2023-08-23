package com.siller.applifting.monitor.user.api;

import lombok.Builder;
import lombok.Getter;

public record UserRegistrationDto (
        String username,
        String email
        ){}
