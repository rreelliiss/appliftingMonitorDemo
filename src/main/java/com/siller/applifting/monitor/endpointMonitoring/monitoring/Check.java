package com.siller.applifting.monitor.endpointMonitoring.monitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Check implements Runnable {

    private MonitoredEndpoint monitoredEndpoint;

    @Override
    public void run() {
        System.out.println("running");
    }
}
