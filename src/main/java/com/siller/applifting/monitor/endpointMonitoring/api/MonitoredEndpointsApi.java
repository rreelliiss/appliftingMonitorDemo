package com.siller.applifting.monitor.endpointMonitoring.api;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/endpoint-monitors")
public interface MonitoredEndpointsApi {

    @PostMapping(consumes = {"application/json"})
    public void createMonitoredEndpoint(
            @RequestBody @Valid MonitoredEndpointRegistrationDto monitorRegistrationDto,
            HttpServletRequest request, HttpServletResponse response);

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    public void updateMonitoredEndpoint(@PathVariable UUID id, @RequestBody MonitoredEndpointUpdatesDto updateRequestDto) throws MonitoredEndpointNotFound;

    @GetMapping("/{id}")
    public MonitoredEndpointDto getMonitoredEndpoint(@PathVariable UUID id) throws MonitoredEndpointNotFound;

    @GetMapping("/{id}/results/top")
    public List<MonitoringResultDto> getMonitoredEndpointResults(@PathVariable("id") UUID id) throws MonitoredEndpointNotFound;

    @DeleteMapping("/{id}")
    public void deleteMonitoredEndpoint(@PathVariable UUID id) throws MonitoredEndpointNotFound;
}
