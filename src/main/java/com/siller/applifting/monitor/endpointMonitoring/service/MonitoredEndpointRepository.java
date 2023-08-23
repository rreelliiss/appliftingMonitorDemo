package com.siller.applifting.monitor.endpointMonitoring.service;

import com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MonitoredEndpointRepository extends CrudRepository<MonitoredEndpoint, UUID> {

    List<MonitoredEndpoint> findAllByOwnerId(UUID ownerId);
}
