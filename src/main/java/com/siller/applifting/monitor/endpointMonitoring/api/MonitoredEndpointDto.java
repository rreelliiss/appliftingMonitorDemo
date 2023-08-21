package com.siller.applifting.monitor.endpointMonitoring.api;


import java.time.LocalDateTime;

public record MonitoredEndpointDto(
        String id,
        String name,
        String url,
        LocalDateTime dateOfCreation,
        LocalDateTime dateOfLastCheck,
        Integer monitoredIntervalInSeconds) {
}
