package com.siller.applifting.monitor.endpointMonitoring.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public record MonitoredEndpointRegistrationDto(
        String name,
        @NotNull String url,
        @NotNull Integer monitoringIntervalInSeconds) {
}
