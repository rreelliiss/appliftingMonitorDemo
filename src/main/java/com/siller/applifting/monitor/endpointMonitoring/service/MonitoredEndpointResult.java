package com.siller.applifting.monitor.endpointMonitoring.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MonitoredEndpointResult {

    private String id;

    private ZonedDateTime dateOfCheck;

    private Integer statusCode;

    private String payload;
}
