package com.siller.applifting.monitor.endpointMonitoring.service;

import java.util.List;
import java.util.UUID;

public interface MonitoredEndpointService {
    UUID createMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpoint, UUID ownerId);

    void updateMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint, MonitoredEndpointUpdates monitoredEndpointUpdates) throws MonitoredEndpointNotFound;

    MonitoredEndpoint getMonitoredEndpointWithoutResults(UUID id) throws MonitoredEndpointNotFound;

    MonitoredEndpoint getMonitoredEndpoint(UUID monitoredEndpointId) throws MonitoredEndpointNotFound;

    void deleteMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint);

    List<MonitoredEndpoint> getMonitoredEndpointsOfUserWithId(UUID userId);
}
