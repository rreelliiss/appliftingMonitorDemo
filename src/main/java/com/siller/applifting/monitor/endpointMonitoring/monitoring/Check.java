package com.siller.applifting.monitor.endpointMonitoring.monitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointResult;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@RequiredArgsConstructor
public class Check implements Runnable {

    private final MonitoredEndpoint monitoredEndpoint;

    @Setter
    private ScheduledFuture<?> scheduledFuture;

    private final MonitoringResultProcessor processor;


    @Override
    public void run() {
        System.out.println("running " + monitoredEndpoint + " " + LocalDateTime.now());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(monitoredEndpoint.getUrl(), HttpMethod.GET, HttpEntity.EMPTY, String.class);
        MonitoredEndpointResult result =  new MonitoredEndpointResult(
                null,
                Instant.now(),
                exchange.getStatusCode().value(),
                exchange.getBody()
        );

        try {
            processor.processMonitoringResult(monitoredEndpoint.getId(), result);
        } catch (MonitoredEndpointNotFound e) {
            System.out.println("Endpoint for monitoring " + monitoredEndpoint.getId() + "is not found, canceling monitoring");
            scheduledFuture.cancel(true);
        }

    }

}
