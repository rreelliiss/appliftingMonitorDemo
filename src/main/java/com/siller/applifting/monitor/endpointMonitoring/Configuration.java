package com.siller.applifting.monitor.endpointMonitoring;

import org.springframework.context.annotation.Bean;

import java.time.Clock;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
