package com.siller.applifting.monitor.endpointMonitoring.persistance;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MonitoredEndpointDbEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String url;

    private Instant dateOfCreation;

    private Instant dateOfLastCheck;

    private Integer monitoringIntervalInSeconds;

    @OneToMany(cascade= CascadeType.ALL, orphanRemoval=true)
    private List<MonitoredEndpointResultDbEntity> monitoredEndpointResults;

}


