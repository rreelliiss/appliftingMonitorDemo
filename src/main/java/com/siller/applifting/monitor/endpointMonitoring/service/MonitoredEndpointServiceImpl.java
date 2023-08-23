package com.siller.applifting.monitor.endpointMonitoring.service;

import com.siller.applifting.monitor.endpointMonitoring.MonitoredEndpointMapper;
import com.siller.applifting.monitor.endpointMonitoring.monitoring.EndpointChecksScheduler;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
    public UUID createMonitoredEndpoint(MonitoredEndpointRegistration monitoredEndpointRegistration, UUID ownerId) {
        MonitoredEndpoint monitoredEndpoint = mapper.toMonitoredEndpoint(monitoredEndpointRegistration, ownerId);
        monitoredEndpoint.setDateOfCreation(Instant.now());
        repository.save(monitoredEndpoint);
        scheduler.scheduleTask(monitoredEndpoint, this::updateMonitoredEndpointWithNewResult);
        return monitoredEndpoint.getId();
    }

    @Override
    @Transactional
    public void updateMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint, MonitoredEndpointUpdates monitoredEndpointUpdates) throws MonitoredEndpointNotFound {
        mapper.update(monitoredEndpoint, monitoredEndpointUpdates);
        repository.save(monitoredEndpoint);
        scheduler.updateTask(monitoredEndpoint, this::updateMonitoredEndpointWithNewResult);
    }

    @Override
    public MonitoredEndpoint getMonitoredEndpointWithoutResults(UUID id) throws MonitoredEndpointNotFound {
        return repository.findById(id).orElseThrow(() -> new MonitoredEndpointNotFound(id));
    }

    @Transactional
    public void updateMonitoredEndpointWithNewResult(UUID monitoredEndpointId, MonitoredEndpointResult result) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = getMonitoredEndpoint(monitoredEndpointId);
        if(monitoredEndpoint.getMonitoredEndpointResults() == null){
            monitoredEndpoint.setMonitoredEndpointResults(new ArrayList<>());
        }
        List<MonitoredEndpointResult> monitoredEndpointResults = monitoredEndpoint.getMonitoredEndpointResults();
        if(monitoredEndpoint.getDateOfLastCheck() == null || result.getDateOfCheck().isAfter(monitoredEndpoint.getDateOfLastCheck())){
            monitoredEndpoint.setDateOfLastCheck(result.getDateOfCheck());
        }
        monitoredEndpointResults.add(result);
        if(monitoredEndpointResults.size() == 11){
            removeOldestResult(monitoredEndpoint);
        }
        repository.save(monitoredEndpoint);
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

    public MonitoredEndpoint getMonitoredEndpoint(UUID monitoredEndpointId) throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = repository.findById(monitoredEndpointId)
                .orElseThrow(() -> new MonitoredEndpointNotFound(monitoredEndpointId));
        Hibernate.initialize(monitoredEndpoint.getMonitoredEndpointResults());
        return monitoredEndpoint;
    }


    @SneakyThrows
    @Override
    public void deleteMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint) {
        UUID id = monitoredEndpoint.getId();
        repository.deleteById(id);
        scheduler.interruptAndRemoveScheduledTaskSync(id);
    }

    @Override
    public List<MonitoredEndpoint> getMonitoredEndpointsOfUserWithId(UUID userId) {
        return repository.findAllByOwnerId(userId);
    }
}
