package com.siller.applifting.monitor.endpointMonitoring.monitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class EndpointChecksSchedulerImpl implements EndpointChecksScheduler {

    private final TaskScheduler taskScheduler;

    public EndpointChecksSchedulerImpl(@Autowired TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    private final Map<UUID, ScheduledFuture<?>> endpointMonitors = new HashMap<>();

    public void scheduleTask(MonitoredEndpoint monitoredEndpoint, MonitoringResultProcessor processor) {
        Check check = new Check(monitoredEndpoint, processor);
        ScheduledFuture<?> schedule = taskScheduler.schedule(
                check,
                new PeriodicTrigger(Duration.ofSeconds(monitoredEndpoint.getMonitoringIntervalInSeconds()))
        );
        check.setScheduledFuture(schedule);
        endpointMonitors.put(monitoredEndpoint.getId(), schedule);
    }

    public void interruptAndRemoveScheduledTaskSync(UUID monitoredEndpointId) throws InterruptedException {
        ScheduledFuture<?> scheduledTask = endpointMonitors.get(monitoredEndpointId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            while(!scheduledTask.isDone()) {
                TimeUnit.SECONDS.sleep(1);
            }
            endpointMonitors.remove(monitoredEndpointId);
        }
    }

    public void updateTask(MonitoredEndpoint monitoredEndpoint, MonitoringResultProcessor processor) {
        interruptAndEndScheduledTask(monitoredEndpoint);
        scheduleTask(monitoredEndpoint, processor);
    }

    private void interruptAndEndScheduledTask(MonitoredEndpoint monitoredEndpoint) {
        ScheduledFuture<?> scheduledTask = endpointMonitors.get(monitoredEndpoint.getId());
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
        }
    }
}
