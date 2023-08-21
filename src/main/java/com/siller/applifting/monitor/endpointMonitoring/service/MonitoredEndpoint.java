package com.siller.applifting.monitor.endpointMonitoring.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonitoredEndpoint {

    private String id;

    private String name;

    private String url;

    private Instant dateOfCreation;

    private Instant dateOfLastCheck;

    private Integer monitoredIntervalInSeconds;

}


