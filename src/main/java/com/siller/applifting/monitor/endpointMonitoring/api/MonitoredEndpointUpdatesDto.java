package com.siller.applifting.monitor.endpointMonitoring.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record MonitoredEndpointUpdatesDto (
        @Size(max = 255) String name,
        @Size(max = 255) String url,
        @Min(1) Integer monitoringIntervalInSeconds){
}
