package com.siller.applifting.monitor.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {

    private UUID id;

    private String username;

    private String mail;

    private String token;

}
