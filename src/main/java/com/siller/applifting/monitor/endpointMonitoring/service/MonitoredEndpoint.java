package com.siller.applifting.monitor.endpointMonitoring.service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class MonitoredEndpoint {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String url;

    private UUID ownerId;

    private Instant dateOfCreation;

    private Instant dateOfLastCheck;

    private Integer monitoringIntervalInSeconds;

    @OneToMany(cascade= CascadeType.ALL, orphanRemoval=true)
    private List<MonitoredEndpointResult> monitoredEndpointResults;

}


