package com.siller.applifting.monitor.endpointMonitoring.api;

import java.time.ZonedDateTime;

public record MonitoringResultDto (
        String id,
        ZonedDateTime dateOfCheck,
        Integer statusCode,
        String payload){}
