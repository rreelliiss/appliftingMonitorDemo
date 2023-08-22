package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.api.*;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointRegistration;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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
    public void createMonitoredEndpoint(
            MonitoredEndpointRegistrationDto monitorRegistrationDto,
            HttpServletRequest request,
            HttpServletResponse response) {
        MonitoredEndpointRegistration monitoredEndpointRegistration = mapper.toMonitoredEndpointRegistration(monitorRegistrationDto);
        UUID monitoredEndpointId = service.createMonitoredEndpoint(monitoredEndpointRegistration);
        String locationLink = getLocationLink(request, monitoredEndpointId);
        response.addHeader("Content-Location", locationLink);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    private static String getLocationLink(HttpServletRequest request, UUID monitoredEndpointId) {
        StringBuffer requestURL = request.getRequestURL();
        return requestURL.append("/").append(monitoredEndpointId).toString();
    }

    @Override
    public void updateMonitoredEndpoint(UUID id, MonitoredEndpointUpdatesDto updateRequestDto) throws MonitoredEndpointNotFound {
        service.updateMonitoredEndpoint(id, mapper.toMonitoredEndpointUpdates(updateRequestDto));
    }

    @Override
    public MonitoredEndpointDto getMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound {
        return mapper.toMonitoredEndpointDto(service.getMonitoredEndpoint(id));
    }

    @Override
    public List<MonitoringResultDto> getMonitoredEndpointResults(UUID monitoredEndpointId) throws MonitoredEndpointNotFound {
        return mapper.toMonitoredEndpointResultDtos(service.getMonitoredEndpointResults(monitoredEndpointId));
    }

    @Override
    public void deleteMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound {
        service.deleteMonitoredEndpoint(id);
    }
}
