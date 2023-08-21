package com.siller.applifting.monitor.endpointMonitoring.service;

import java.util.List;

public interface MonitoredEndpointService {
    String createMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpoint);

    void updateMonitoredEndpoint(MonitoredEndpointUpdates monitoredEndpoint);

    MonitoredEndpoint getMonitoredEndpoint(String id);

    List<MonitoredEndpointResult> getMonitoredEndpointResults(String MonitoredEndpointId);

    void deleteMonitoredEndpoint(String id);
}
