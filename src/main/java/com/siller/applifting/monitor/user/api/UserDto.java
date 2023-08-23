package com.siller.applifting.monitor.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDto(@Size(max = 255) String username, @Email @Size(max = 255) String email, @NotNull String token) {

}
