package com.siller.applifting.monitor.endpointMonitoring.api;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record MonitoredEndpointDto(
        UUID id,
        @Size(max = 255) String name,
        @Size(max = 255) String url,
        LocalDateTime dateOfCreation,
        LocalDateTime dateOfLastCheck,
        @Min(1) Integer monitoringIntervalInSeconds) {
}
