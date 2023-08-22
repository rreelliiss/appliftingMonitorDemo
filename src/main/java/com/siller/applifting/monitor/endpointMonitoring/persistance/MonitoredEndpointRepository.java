package com.siller.applifting.monitor.endpointMonitoring.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MonitoredEndpointRepository extends CrudRepository<MonitoredEndpointDbEntity, UUID> {

}
