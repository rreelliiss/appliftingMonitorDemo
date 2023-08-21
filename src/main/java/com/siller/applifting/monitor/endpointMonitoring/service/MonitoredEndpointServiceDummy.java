package com.siller.applifting.monitor.endpointMonitoring.service;

import java.util.List;

public class MonitoredEndpointServiceDummy implements MonitoredEndpointService{
    @Override
    public String createMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpoint) {
        return null;
    }

    @Override
    public void updateMonitoredEndpoint(MonitoredEndpointUpdates monitoredEndpoint) {

    }

    @Override
    public MonitoredEndpoint getMonitoredEndpoint(String id) {
        return null;
    }

    @Override
    public List<MonitoredEndpointResult> getMonitoredEndpointResults(String monitoredEndpointId) {

        return null;
    }

    @Override
    public void deleteMonitoredEndpoint(String id) {

    }
}
