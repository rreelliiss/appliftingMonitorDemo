package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointRegistrationDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointUpdatesDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointsApi;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoringResultDto;
import com.siller.applifting.monitor.endpointMonitoring.service.*;
import com.siller.applifting.monitor.security.NotAuthenticated;
import com.siller.applifting.monitor.security.NotAuthorized;
import com.siller.applifting.monitor.user.service.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            HttpServletResponse response) throws NotAuthenticated {
        UUID userId = getIdOfAuthenticatedUser();
        MonitoredEndpointRegistration monitoredEndpointRegistration = mapper.toMonitoredEndpointRegistration(monitorRegistrationDto);
        UUID monitoredEndpointId = service.createMonitoredEndpoint(monitoredEndpointRegistration, userId);
        String locationLink = getLocationLink(request, monitoredEndpointId);
        response.addHeader("Content-Location", locationLink);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    private static UUID getIdOfAuthenticatedUser() throws NotAuthenticated {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ensureUserIsAuthenticated(authentication);
        return (UUID) authentication.getPrincipal();
    }

    private static void ensureUserIsAuthenticated(Authentication authentication) throws NotAuthenticated {
        if(authentication == null || authentication.getPrincipal() == null){
            throw new NotAuthenticated();
        }
    }

    private static String getLocationLink(HttpServletRequest request, UUID monitoredEndpointId) {
        StringBuffer requestURL = request.getRequestURL();
        return requestURL.append("/").append(monitoredEndpointId).toString();
    }

    @Override
    //@Transactional
    public void updateMonitoredEndpoint(UUID id, MonitoredEndpointUpdatesDto updateRequestDto) throws MonitoredEndpointNotFound, NotAuthenticated, NotAuthorized {
        MonitoredEndpoint monitoredEndpoint = service.getMonitoredEndpointWithoutResults(id);
        ensureAuthorization(monitoredEndpoint);
        MonitoredEndpointUpdates monitoredEndpointUpdates = mapper.toMonitoredEndpointUpdates(updateRequestDto);
        service.updateMonitoredEndpoint(monitoredEndpoint, monitoredEndpointUpdates);
    }

    private static void ensureAuthorization(MonitoredEndpoint monitoredEndpoint) throws NotAuthorized, NotAuthenticated {
        UUID userId = getIdOfAuthenticatedUser();
        if(!monitoredEndpoint.getOwnerId().equals(userId)){
            throw new NotAuthorized();
        }
    }

    @Override
    public MonitoredEndpointDto getMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound, NotAuthenticated, NotAuthorized {
        MonitoredEndpoint monitoredEndpointWithoutResults = service.getMonitoredEndpointWithoutResults(id);
        ensureAuthorization(monitoredEndpointWithoutResults);
        return mapper.toMonitoredEndpointDto(monitoredEndpointWithoutResults);
    }

    @Override
    public List<MonitoringResultDto> getMonitoredEndpointResults(UUID monitoredEndpointId) throws MonitoredEndpointNotFound, NotAuthorized, NotAuthenticated {
        MonitoredEndpoint monitoredEndpoint = service.getMonitoredEndpoint(monitoredEndpointId);
        ensureAuthorization(monitoredEndpoint);
        List<MonitoredEndpointResult> monitoredEndpointResults = monitoredEndpoint.getMonitoredEndpointResults();
        List<MonitoringResultDto> monitoringResultDtos = mapper.toMonitoredEndpointResultDtos(monitoredEndpointResults);
        monitoringResultDtos.sort(Comparator.comparing(MonitoringResultDto::dateOfCheck));
        return monitoringResultDtos;
    }

    @Override
    public List<MonitoredEndpointDto> getMonitoredEndpoints() throws NotAuthenticated, NotAuthorized {
        UUID userId = getIdOfAuthenticatedUser();
        List<MonitoredEndpoint> monitoredEndpoints = service.getMonitoredEndpointsOfUserWithId(userId);
        for(MonitoredEndpoint monitoredEndpoint: monitoredEndpoints){
            ensureAuthorization(monitoredEndpoint);
        }
        return   monitoredEndpoints.stream().map(mapper::toMonitoredEndpointDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound, NotAuthorized, NotAuthenticated {
        MonitoredEndpoint monitoredEndpoint = service.getMonitoredEndpoint(id);
        ensureAuthorization(monitoredEndpoint);
        service.deleteMonitoredEndpoint(monitoredEndpoint);
    }
}
