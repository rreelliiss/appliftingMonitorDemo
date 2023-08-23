package com.siller.applifting.monitor.endpointMonitoring.monitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointResult;

import java.util.UUID;

@FunctionalInterface
public interface MonitoringResultProcessor {

    void processMonitoringResult(UUID id, MonitoredEndpointResult monitoredEndpointResult) throws MonitoredEndpointNotFound;

}
