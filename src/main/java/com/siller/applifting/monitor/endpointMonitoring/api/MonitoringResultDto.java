package com.siller.applifting.monitor.endpointMonitoring.api;

import java.time.ZonedDateTime;
import java.util.UUID;

public record MonitoringResultDto (
        UUID id,
        ZonedDateTime dateOfCheck,
        Integer statusCode,
        String payload){}
