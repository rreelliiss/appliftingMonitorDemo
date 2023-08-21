package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointRegistrationDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointUpdatesDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoringResultDto;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointRegistration;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointResult;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointUpdates;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MonitoredEndpointMapper {

    public abstract MonitoredEndpointRegistration toMonitoredEndpointRegistration(MonitoredEndpointRegistrationDto m);

    public abstract  MonitoredEndpointUpdates toMonitoredEndpointUpdates(MonitoredEndpointUpdatesDto updateRequestDto);

    public abstract MonitoredEndpointDto getMonitoredEndpointDto(MonitoredEndpoint monitoredEndpoint);

    public abstract  List<MonitoringResultDto> getMonitoredEndpointResultDtos(List<MonitoredEndpointResult> monitoredEndpointResults);

    protected LocalDateTime map(Instant instant){
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }
}
