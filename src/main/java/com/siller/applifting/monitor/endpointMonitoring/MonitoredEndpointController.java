package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.api.*;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MonitoredEndpointController implements MonitoredEndpointsApi {

    private final MonitoredEndpointService service;
    private final MonitoredEndpointMapper mapper;

    MonitoredEndpointController(
            @Autowired MonitoredEndpointService monitoredEndpointService,
            @Autowired MonitoredEndpointMapper monitoredEndpointMapper){
        service = monitoredEndpointService;
        mapper = monitoredEndpointMapper;
    }

    @Override
    public void createMonitoredEndpoint(MonitoredEndpointRegistrationDto monitorRegistrationDto) {
        service.createMonitoredEndpoint(mapper.toMonitoredEndpointRegistration(monitorRegistrationDto));
    }

    @Override
    public void updateMonitoredEndpoint(String id, MonitoredEndpointUpdatesDto updateRequestDto) {
        service.updateMonitoredEndpoint(mapper.toMonitoredEndpointUpdates(updateRequestDto));
    }

    @Override
    public MonitoredEndpointDto getMonitoredEndpoint(String id) {
        return mapper.getMonitoredEndpointDto(service.getMonitoredEndpoint(id));
    }

    @Override
    public List<MonitoringResultDto> getMonitoredEndpointResults(String monitoredEndpointId) {
        return mapper.getMonitoredEndpointResultDtos(service.getMonitoredEndpointResults(monitoredEndpointId));
    }

    @Override
    public void deleteMonitoredEndpoint(String id) {
        service.deleteMonitoredEndpoint(id);
    }
}
