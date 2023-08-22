package com.siller.applifting.monitor.endpointMonitoring.api;


import java.time.LocalDateTime;
import java.util.UUID;

public record MonitoredEndpointDto(
        UUID id,
        String name,
        String url,
        LocalDateTime dateOfCreation,
        LocalDateTime dateOfLastCheck,
        Integer monitoringIntervalInSeconds) {
}
