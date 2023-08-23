package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointRegistrationDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointUpdatesDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointsApi;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoringResultDto;
import com.siller.applifting.monitor.endpointMonitoring.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
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
        MonitoredEndpointUpdates monitoredEndpoint = mapper.toMonitoredEndpointUpdates(updateRequestDto);
        service.updateMonitoredEndpoint(id, monitoredEndpoint);
    }

    @Override
    public MonitoredEndpointDto getMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound {
        return mapper.toMonitoredEndpointDto(service.getMonitoredEndpointWithoutResults(id));
    }

    @Override
    public List<MonitoringResultDto> getMonitoredEndpointResults(UUID monitoredEndpointId) throws MonitoredEndpointNotFound {
        List<MonitoredEndpointResult> monitoredEndpointResults = service.getMonitoredEndpointResults(monitoredEndpointId);
        List<MonitoringResultDto> monitoringResultDtos = mapper.toMonitoredEndpointResultDtos(monitoredEndpointResults);
        monitoringResultDtos.sort(Comparator.comparing(MonitoringResultDto::dateOfCheck));
        return monitoringResultDtos;
    }

    @Override
    public void deleteMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound {
        service.deleteMonitoredEndpoint(id);
    }
}
