package com.siller.applifting.monitor.endpointMonitoring.api;

public record MonitoredEndpointUpdatesDto (
    String name,
    String url,
    Integer monitoringIntervalInSeconds){
}
