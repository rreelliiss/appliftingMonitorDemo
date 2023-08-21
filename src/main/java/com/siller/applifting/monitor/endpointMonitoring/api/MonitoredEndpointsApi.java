package com.siller.applifting.monitor.endpointMonitoring.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(consumes = {"application/json"})
public interface MonitoredEndpointsApi {

    @PostMapping("/endpoint-monitors")
    public void createMonitoredEndpoint(@RequestBody @Valid MonitoredEndpointRegistrationDto monitorRegistrationDto);

    @PutMapping("/endpoint-monitor/{id}")
    public void updateMonitoredEndpoint(@PathVariable String id, @RequestBody MonitoredEndpointUpdatesDto updateRequestDto);

    @GetMapping("/endpoint-monitor/{id}")
    public MonitoredEndpointDto getMonitoredEndpoint(@PathVariable String id);

    @GetMapping("/endpoint-monitor/{id}/results/top")
    public List<MonitoringResultDto> getMonitoredEndpointResults(@PathVariable String id);

    @DeleteMapping("/endpoint-monitor/{id}")
    public void deleteMonitoredEndpoint(@PathVariable String id);
}
