package com.siller.applifting.monitor.endpointMonitoring.api;

import java.time.LocalDateTime;
import java.util.UUID;

public record MonitoringResultDto (
        UUID id,
        LocalDateTime dateOfCheck,
        Integer statusCode,
        String payload){}
