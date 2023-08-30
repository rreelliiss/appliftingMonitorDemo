package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointRegistrationDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointUpdatesDto;
import com.siller.applifting.monitor.endpointMonitoring.api.MonitoringResultDto;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointRegistration;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointResult;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointUpdates;
import org.hibernate.Hibernate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class MonitoredEndpointMapper {

    public abstract MonitoredEndpointRegistration toMonitoredEndpointRegistration(MonitoredEndpointRegistrationDto m);

    public abstract  MonitoredEndpointUpdates toMonitoredEndpointUpdates(MonitoredEndpointUpdatesDto updateRequestDto);

    public abstract MonitoredEndpointDto toMonitoredEndpointDto(MonitoredEndpoint monitoredEndpoint);

    public abstract  List<MonitoringResultDto> toMonitoredEndpointResultDtos(List<MonitoredEndpointResult> monitoredEndpointResults);

    public abstract MonitoredEndpoint toMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpointRegistration, UUID ownerId);

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
