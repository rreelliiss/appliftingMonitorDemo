package com.siller.applifting.monitor.endpointMonitoring.monitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;

import java.util.UUID;

public interface EndpointChecksScheduler {

    void scheduleTask(MonitoredEndpoint monitoredEndpoint, MonitoringResultProcessor processor);

    void interruptAndRemoveScheduledTaskSync(UUID monitoredEndpointId) throws InterruptedException;

    void updateTask(MonitoredEndpoint monitoredEndpoint, MonitoringResultProcessor processor);
}
