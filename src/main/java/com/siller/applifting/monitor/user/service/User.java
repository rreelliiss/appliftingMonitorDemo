package com.siller.applifting.monitor.user.service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "users",
        indexes = {
        @Index(name = "tokenIndex", columnList = "token", unique = true)
})
@Getter
@NoArgsConstructor
@Setter
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String mail;

    private String token;

}
