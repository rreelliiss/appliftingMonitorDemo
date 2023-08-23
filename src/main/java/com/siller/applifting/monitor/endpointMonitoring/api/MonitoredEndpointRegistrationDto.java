package com.siller.applifting.monitor.endpointMonitoring.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MonitoredEndpointRegistrationDto(
        @Size(max = 255) String name,
        @NotNull @Size(max = 255) String url,
        @NotNull @Min(1) Integer monitoringIntervalInSeconds) {
}
