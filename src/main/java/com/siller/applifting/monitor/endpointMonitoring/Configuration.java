package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.persistance.MonitoredEndpointRepository;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointService;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Autowired
    private MonitoredEndpointMapper monitoredEndpointMapper;

    @Autowired
    private MonitoredEndpointRepository monitoredEndpointRepository;

    @Bean
    public MonitoredEndpointService monitoredEndpointService(){
        return new MonitoredEndpointServiceImpl(monitoredEndpointMapper, monitoredEndpointRepository);
    }

}
