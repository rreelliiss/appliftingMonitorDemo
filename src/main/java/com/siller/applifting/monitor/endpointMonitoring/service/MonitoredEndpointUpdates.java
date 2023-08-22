package com.siller.applifting.monitor.endpointMonitoring.service;

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
public class MonitoredEndpointUpdates {

    private String name;

    private String url;

    private Integer monitoringIntervalInSeconds;

}