package com.siller.applifting.monitor.endpointMonitoring.service;

import com.siller.applifting.monitor.endpointMonitoring.MonitoredEndpointMapper;
import com.siller.applifting.monitor.endpointMonitoring.persistance.MonitoredEndpointDbEntity;
import com.siller.applifting.monitor.endpointMonitoring.persistance.MonitoredEndpointRepository;
import com.siller.applifting.monitor.endpointMonitoring.monitoring.EndpointChecksScheduler;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MonitoredEndpointServiceImpl implements MonitoredEndpointService {

    private final MonitoredEndpointMapper mapper;

    private final MonitoredEndpointRepository repository;

    private final EndpointChecksScheduler scheduler;

    public MonitoredEndpointServiceImpl(
            @Autowired MonitoredEndpointMapper monitoredEndpointMapper,
            @Autowired MonitoredEndpointRepository monitoredEndpointRepository,
            @Autowired EndpointChecksScheduler scheduler){
        mapper = monitoredEndpointMapper;
        repository = monitoredEndpointRepository;
        this.scheduler = scheduler;
    }

    @Override
    @Transactional
    public UUID createMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpointRegistration) {
        MonitoredEndpoint monitoredEndpoint = mapper.toMonitoredEndpoint(monitoredEndpointRegistration);
        monitoredEndpoint.setDateOfCreation(Instant.now());
        MonitoredEndpointDbEntity monitoredEndpointDbEntity = mapper.toMonitoredEndpointDbEntity(monitoredEndpoint);
        MonitoredEndpointDbEntity savedEntity = repository.save(monitoredEndpointDbEntity);
        monitoredEndpoint.setId(savedEntity.getId());
        scheduler.scheduleTask(monitoredEndpoint, this::updateMonitoredEndpointWithNewResult);
        return savedEntity.getId();
    }

    @Override
    @Transactional
    public void updateMonitoredEndpoint(UUID id, MonitoredEndpointUpdates monitoredEndpointUpdates) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = getMonitoredEndpoint(id);
        mapper.update(monitoredEndpoint, monitoredEndpointUpdates);
        repository.save(mapper.toMonitoredEndpointDbEntity(monitoredEndpoint));
        scheduler.updateTask(monitoredEndpoint, this::updateMonitoredEndpointWithNewResult);
    }

    @Override
    public MonitoredEndpoint getMonitoredEndpointWithoutResults(UUID id) throws MonitoredEndpointNotFound {
        MonitoredEndpointDbEntity monitoredEndpointDbEntity = repository.findById(id).orElseThrow(() -> new MonitoredEndpointNotFound(id));
        return mapper.toMonitoredEndpoint(
                monitoredEndpointDbEntity
        );
    }

    @Override
    @Transactional
    public List<MonitoredEndpointResult> getMonitoredEndpointResults(UUID monitoredEndpointId) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = getMonitoredEndpoint(monitoredEndpointId);
        return monitoredEndpoint.getMonitoredEndpointResults();
    }

    @Transactional
    public void updateMonitoredEndpointWithNewResult(UUID monitoredEndpointId, MonitoredEndpointResult result) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = getMonitoredEndpoint(monitoredEndpointId);
        List<MonitoredEndpointResult> monitoredEndpointResults = monitoredEndpoint.getMonitoredEndpointResults();
        if(monitoredEndpoint.getDateOfLastCheck() == null || result.getDateOfCheck().isAfter(monitoredEndpoint.getDateOfLastCheck())){
            monitoredEndpoint.setDateOfLastCheck(result.getDateOfCheck());
        }
        monitoredEndpointResults.add(result);
        if(monitoredEndpointResults.size() == 11){
            removeOldestResult(monitoredEndpoint);
        }
        repository.save(mapper.toMonitoredEndpointDbEntity(monitoredEndpoint));
    }

    private static void removeOldestResult(MonitoredEndpoint monitoredEndpoint) {
        Instant oldest = monitoredEndpoint.getDateOfLastCheck();
        int oldestIndex = 0;
        List<MonitoredEndpointResult> monitoredEndpointResults = monitoredEndpoint.getMonitoredEndpointResults();
        for (int i = 0; i < monitoredEndpointResults.size(); i++) {
            if (monitoredEndpointResults.get(i).getDateOfCheck().isBefore(oldest)) {
                oldest = monitoredEndpointResults.get(i).getDateOfCheck();
                oldestIndex = i;
            }
        }
        monitoredEndpointResults.remove(oldestIndex);
    }

    private MonitoredEndpoint getMonitoredEndpoint(UUID monitoredEndpointId) throws MonitoredEndpointNotFound {
        MonitoredEndpointDbEntity monitoredEndpointDbEntity = repository.findById(monitoredEndpointId)
                .orElseThrow(() -> new MonitoredEndpointNotFound(monitoredEndpointId));
        Hibernate.initialize(monitoredEndpointDbEntity.getMonitoredEndpointResults());
        return mapper.toMonitoredEndpoint(monitoredEndpointDbEntity);
    }


    @SneakyThrows
    @Override
    @Transactional
    public void deleteMonitoredEndpoint(UUID id) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = getMonitoredEndpoint(id);
        repository.deleteById(monitoredEndpoint.getId());
        scheduler.interruptAndRemoveScheduledTaskSync(id);
    }
}
