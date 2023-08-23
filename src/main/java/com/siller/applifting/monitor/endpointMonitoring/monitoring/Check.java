package com.siller.applifting.monitor.endpointMonitoring.monitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointResult;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@RequiredArgsConstructor
public class Check implements Runnable {

    Logger logger = LoggerFactory.getLogger(Check.class);

    private final MonitoredEndpoint monitoredEndpoint;

    @Setter
    private ScheduledFuture<?> scheduledFuture;

    private final MonitoringResultProcessor processor;


    @Override
    public void run() {
        logger.debug("running check for " + monitoredEndpoint.getId() + " " + LocalDateTime.now());

        RestTemplate restTemplate = new RestTemplate();
        MonitoredEndpointResult result;
        Instant timestamp = Instant.now();
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(monitoredEndpoint.getUrl(), HttpMethod.GET, HttpEntity.EMPTY, String.class);
            result =  new MonitoredEndpointResult(
                    null,
                    timestamp,
                    exchange.getStatusCode().value(),
                    exchange.getBody()
            );
        } catch (Exception e){
            result =  new MonitoredEndpointResult(
                    null,
                    timestamp,
                    null,
                    e.getMessage()
            );
        }

        logger.info(
                "Result check for " + monitoredEndpoint.getId()
                + "(name:"+ monitoredEndpoint.getName()+", url:" + monitoredEndpoint.getUrl()+ ")" + " at " + LocalDateTime.now()
                + "is: " + result.toString()
        );
        try {
            processor.processMonitoringResult(monitoredEndpoint.getId(), result);
        } catch (MonitoredEndpointNotFound e) {
            logger.warn("Endpoint for monitoring " + monitoredEndpoint.getId() + "is not found, canceling monitoring");
            scheduledFuture.cancel(true);
        }

    }

}
