package com.siller.applifting.monitor.endpointMonitoring.persistance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class MonitoredEndpointResultDbEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant dateOfCheck;

    private Integer statusCode;

    @Column(columnDefinition = "LONGTEXT")
    private String payload;
}