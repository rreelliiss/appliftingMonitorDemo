package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointRegistrationDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointUpdatesDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoringResultDto;
import com.siller.applifting.monitor.endpointMonitoring.persistance.MonitoredEndpointDbEntity;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointRegistration;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointResult;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointUpdates;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MonitoredEndpointMapper {

    public abstract MonitoredEndpointRegistration toMonitoredEndpointRegistration(MonitoredEndpointRegistrationDto m);

    public abstract  MonitoredEndpointUpdates toMonitoredEndpointUpdates(MonitoredEndpointUpdatesDto updateRequestDto);

    public abstract MonitoredEndpointDto toMonitoredEndpointDto(MonitoredEndpoint monitoredEndpoint);

    public abstract  List<MonitoringResultDto> toMonitoredEndpointResultDtos(List<MonitoredEndpointResult> monitoredEndpointResults);

    public abstract MonitoredEndpointDbEntity toMonitoredEndpointDbEntity(MonitoredEndpoint monitoredEndpoint);

    public abstract MonitoredEndpoint toMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpointRegistration);

    public abstract MonitoredEndpoint toMonitoredEndpoint(MonitoredEndpointDbEntity monitoredEndpointDbEntity);

    protected LocalDateTime map(Instant instant){
        if(instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
                nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    public abstract void update(@MappingTarget MonitoredEndpoint monitoredEndpoint, MonitoredEndpointUpdates monitoredEndpointUpdates);
}
