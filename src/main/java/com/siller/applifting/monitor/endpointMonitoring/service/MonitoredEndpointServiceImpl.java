package com.siller.applifting.monitor.endpointMonitoring.service;

import com.siller.applifting.monitor.endpointMonitoring.MonitoredEndpointMapper;
import com.siller.applifting.monitor.endpointMonitoring.persistance.MonitoredEndpointDbEntity;
import com.siller.applifting.monitor.endpointMonitoring.persistance.MonitoredEndpointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MonitoredEndpointServiceImpl implements MonitoredEndpointService {

    private final MonitoredEndpointMapper mapper;
    private final MonitoredEndpointRepository repository;

    public MonitoredEndpointServiceImpl(
            @Autowired MonitoredEndpointMapper monitoredEndpointMapper,
            @Autowired MonitoredEndpointRepository monitoredEndpointRepository){
        mapper = monitoredEndpointMapper;
        repository = monitoredEndpointRepository;
    }

    @Override
    @Transactional
    public UUID createMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpointRegistration) {
        MonitoredEndpoint monitoredEndpoint = mapper.toMonitoredEndpoint(monitoredEndpointRegistration);
        monitoredEndpoint.setDateOfCreation(Instant.now());
        MonitoredEndpointDbEntity monitoredEndpointDbEntity = mapper.toMonitoredEndpointDbEntity(monitoredEndpoint);
        MonitoredEndpointDbEntity savedEntity = repository.save(monitoredEndpointDbEntity);
        return savedEntity.getId();
    }

    @Override
    @Transactional
    public void updateMonitoredEndpoint(UUID id, MonitoredEndpointUpdates monitoredEndpointUpdates) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = getMonitoredEndpoint(id);
        mapper.update(monitoredEndpoint, monitoredEndpointUpdates);
        repository.save(mapper.toMonitoredEndpointDbEntity(monitoredEndpoint));
    }

    @Override
    public MonitoredEndpoint getMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound {
        return mapper.toMonitoredEndpoint(
                repository.findById(id).orElseThrow(()->new MonitoredEndpointNotFound(id))
        );
    }

    @Override
    public List<MonitoredEndpointResult> getMonitoredEndpointResults(UUID monitoredEndpointId) throws MonitoredEndpointNotFound {
        return getMonitoredEndpoint(monitoredEndpointId).getMonitoredEndpointResults();
    }

    @Override
    @Transactional
    public void deleteMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = getMonitoredEndpoint(id);
        repository.deleteById(monitoredEndpoint.getId());
    }
}
