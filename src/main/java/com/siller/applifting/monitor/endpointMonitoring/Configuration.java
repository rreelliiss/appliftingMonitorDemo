package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointService;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointServiceDummy;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public MonitoredEndpointService monitoredEndpointService(){
        return new MonitoredEndpointServiceDummy();
    }

}
