package com.siller.applifting.monitor.endpointMonitoring.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class MonitoredEndpointResult {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant dateOfCheck;

    private Integer statusCode;

    @Column(columnDefinition = "LONGTEXT")
    private String payload;
}
