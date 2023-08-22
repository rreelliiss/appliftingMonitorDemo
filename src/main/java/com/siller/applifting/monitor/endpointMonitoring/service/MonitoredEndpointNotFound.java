package com.siller.applifting.monitor.endpointMonitoring.service;

import java.util.UUID;

public class MonitoredEndpointNotFound extends Exception {
    public MonitoredEndpointNotFound(UUID id) {
        super("Monitored exception " + id.toString() + " was not found.");
    }
}
