package com.siller.applifting.monitor.endpointMonitoring.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonitoredEndpointRegistration {

    private String name;

    private String url;

    private Integer monitoringIntervalInSeconds;

}
