package com.siller.applifting.monitor.endpointMonitoring.service;

import java.util.List;
import java.util.UUID;

public interface MonitoredEndpointService {
    UUID createMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpoint);

    void updateMonitoredEndpoint(UUID id, MonitoredEndpointUpdates monitoredEndpoint) throws MonitoredEndpointNotFound;

    MonitoredEndpoint getMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound;

    List<MonitoredEndpointResult> getMonitoredEndpointResults(UUID MonitoredEndpointId) throws MonitoredEndpointNotFound;

    void deleteMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound;
}
