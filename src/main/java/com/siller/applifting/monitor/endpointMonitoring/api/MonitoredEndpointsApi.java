package com.siller.applifting.monitor.endpointMonitoring.api;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import com.siller.applifting.monitor.security.NotAuthenticated;
import com.siller.applifting.monitor.security.NotAuthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointsApi.MONITORED_ENDPOINT_PATH_PREFIX;

@RequestMapping(path = MONITORED_ENDPOINT_PATH_PREFIX)
public interface MonitoredEndpointsApi {

    public static final String MONITORED_ENDPOINT_PATH_PREFIX = "/endpoint-monitors";

    @PostMapping(consumes = {"application/json"})
    void createMonitoredEndpoint(
            @RequestBody @Valid MonitoredEndpointRegistrationDto monitorRegistrationDto,
            HttpServletRequest request, HttpServletResponse response) throws NotAuthenticated;

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    void updateMonitoredEndpoint(@PathVariable UUID id, @RequestBody @Valid MonitoredEndpointUpdatesDto updateRequestDto) throws MonitoredEndpointNotFound, NotAuthenticated, NotAuthorized;

    @GetMapping("/{id}")
    MonitoredEndpointDto getMonitoredEndpoint(@PathVariable UUID id) throws MonitoredEndpointNotFound, NotAuthenticated, NotAuthorized;

    @GetMapping("/{id}/results/top")
    public List<MonitoringResultDto> getMonitoredEndpointResults(@PathVariable("id") UUID id) throws MonitoredEndpointNotFound, NotAuthorized, NotAuthenticated;

    @GetMapping
    List<MonitoredEndpointDto> getMonitoredEndpoints() throws NotAuthenticated, NotAuthorized;

    @DeleteMapping("/{id}")
    void deleteMonitoredEndpoint(@PathVariable UUID id) throws MonitoredEndpointNotFound, NotAuthorized, NotAuthenticated;
}
