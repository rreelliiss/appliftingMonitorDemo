package com.siller.applifting.monitor.endpointMonitoring.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MonitoredEndpoint {

    private UUID id;

    private String name;

    private String url;

    private Instant dateOfCreation;

    private Instant dateOfLastCheck;

    private Integer monitoringIntervalInSeconds;

    private List<MonitoredEndpointResult> monitoredEndpointResults;

}


