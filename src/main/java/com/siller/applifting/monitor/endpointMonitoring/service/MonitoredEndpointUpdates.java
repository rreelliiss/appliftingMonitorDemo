package com.siller.applifting.monitor.endpointMonitoring.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonitoredEndpointUpdates {

    private String name;

    private String url;

    private Integer monitoringIntervalInSeconds;

}