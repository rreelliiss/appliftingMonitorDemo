package com.siller.applifting.monitor;

import com.siller.applifting.monitor.endpointMonitoring.monitoring.EndpointChecksScheduler;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpoint;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointRegistration;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointService;
import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointUpdates;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MonitoredEndpointServiceTest {


    @MockBean
    EndpointChecksScheduler endpointChecksScheduler;

    @MockBean
    Clock clock;

    @Autowired
    MonitoredEndpointService service;

    @BeforeEach
    public void setup(){
        when(clock.instant()).thenReturn(Instant.parse("2023-08-24T08:19:18.695858456Z"));
    }

    @Test
    public void gettingCreatedMonitoredEndpoint_returnsExpectedCreatedEndpoint() throws MonitoredEndpointNotFound {
        MonitoredEndpointRegistration monitoredEndpointRegistration = new MonitoredEndpointRegistration(
                "test",
                "http:/example.com",
                1
                );
        UUID ownerId = UUID.fromString("185bb486-4e71-42ad-bff3-d6f587f7a548");
        UUID storedMonitoredEndpointId = service.createMonitoredEndpoint(monitoredEndpointRegistration, ownerId);
        MonitoredEndpoint retrievedMonitoredEndpoint = service.getMonitoredEndpoint(storedMonitoredEndpointId);

        assertNotNull(retrievedMonitoredEndpoint.getId());
        assertEquals("http:/example.com", retrievedMonitoredEndpoint.getUrl());
        assertEquals("test", retrievedMonitoredEndpoint.getName());
        assertEquals(1, retrievedMonitoredEndpoint.getMonitoringIntervalInSeconds());
        assertEquals(Instant.parse("2023-08-24T08:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfCreation());
        assertEquals(null, retrievedMonitoredEndpoint.getDateOfLastCheck());
        assertEquals(null, retrievedMonitoredEndpoint.getMonitoredEndpointResults());
    }

    @Test
    public void gettingCreatedMonitoredEndpoint_throwsNotFoundException_whenMonitoredEndpointWasDeleted() throws MonitoredEndpointNotFound {

        MonitoredEndpointRegistration monitoredEndpointRegistration = new MonitoredEndpointRegistration(
                "test",
                "http:/example.com",
                1
        );
        UUID ownerId = UUID.fromString("185bb486-4e71-42ad-bff3-d6f587f7a548");
        UUID storedMonitoredEndpointId = service.createMonitoredEndpoint(monitoredEndpointRegistration, ownerId);
        MonitoredEndpoint retrievedMonitoredEndpoint = service.getMonitoredEndpoint(storedMonitoredEndpointId);
        service.deleteMonitoredEndpoint(retrievedMonitoredEndpoint);

        try {
            service.getMonitoredEndpoint(storedMonitoredEndpointId);
            Assertions.fail();
        } catch (MonitoredEndpointNotFound e){
            assertEquals("Monitored exception " + storedMonitoredEndpointId+ " was not found.", e.getMessage());
        }


    }



    @Test
    public void updatedMonitoredEndpoint_returnsExpectedCreatedEndpoint_whenProvidedAllUpdates() throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint(
                null,
                "test",
                "http://example.com",
                UUID.fromString("0000486-4e71-42ad-bff3-d6f587f7a000"),
                Instant.parse("2023-07-24T07:19:18.695858456Z"),
                Instant.parse("2023-08-24T08:19:18.695858456Z"),
                3,
                null
        );
        MonitoredEndpointUpdates updates = new MonitoredEndpointUpdates(
                "new name",
                "http://example.com/new",
                4
        );
        MonitoredEndpoint savedMonitoredEndpoint = service.updateMonitoredEndpoint(monitoredEndpoint, updates);

        MonitoredEndpoint retrievedMonitoredEndpoint = service.getMonitoredEndpoint(savedMonitoredEndpoint.getId());

        assertEquals("http://example.com/new", retrievedMonitoredEndpoint.getUrl());
        assertEquals("new name", retrievedMonitoredEndpoint.getName());
        assertEquals(4, retrievedMonitoredEndpoint.getMonitoringIntervalInSeconds());
        assertEquals(Instant.parse("2023-07-24T07:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfCreation());
        assertEquals(Instant.parse("2023-08-24T08:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfLastCheck());
        assertEquals(null, retrievedMonitoredEndpoint.getMonitoredEndpointResults());
    }

    @Test
    public void updatedMonitoredEndpoint_returnsExpectedCreatedEndpoint_whenProvidedOnlyName() throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint(
                null,
                "test",
                "http://example.com",
                UUID.fromString("0000486-4e71-42ad-bff3-d6f587f7a000"),
                Instant.parse("2023-07-24T07:19:18.695858456Z"),
                Instant.parse("2023-08-24T08:19:18.695858456Z"),
                3,
                null
        );
        MonitoredEndpointUpdates updates = new MonitoredEndpointUpdates(
                "new name",
                null,
                null
        );
        MonitoredEndpoint savedMonitoredEndpoint = service.updateMonitoredEndpoint(monitoredEndpoint, updates);

        MonitoredEndpoint retrievedMonitoredEndpoint = service.getMonitoredEndpoint(savedMonitoredEndpoint.getId());

        assertEquals("http://example.com", retrievedMonitoredEndpoint.getUrl());
        assertEquals("new name", retrievedMonitoredEndpoint.getName());
        assertEquals(3, retrievedMonitoredEndpoint.getMonitoringIntervalInSeconds());
        assertEquals(Instant.parse("2023-07-24T07:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfCreation());
        assertEquals(Instant.parse("2023-08-24T08:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfLastCheck());
        assertEquals(null, retrievedMonitoredEndpoint.getMonitoredEndpointResults());
    }

    @Test
    public void updatedMonitoredEndpoint_returnsExpectedCreatedEndpoint_whenProvidedOnlyUrl() throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint(
                null,
                "test",
                "http://example.com",
                UUID.fromString("0000486-4e71-42ad-bff3-d6f587f7a000"),
                Instant.parse("2023-07-24T07:19:18.695858456Z"),
                Instant.parse("2023-08-24T08:19:18.695858456Z"),
                3,
                null
        );
        MonitoredEndpointUpdates updates = new MonitoredEndpointUpdates(
                null,
                "http://example.com/new",
                null
        );
        MonitoredEndpoint savedMonitoredEndpoint = service.updateMonitoredEndpoint(monitoredEndpoint, updates);

        MonitoredEndpoint retrievedMonitoredEndpoint = service.getMonitoredEndpoint(savedMonitoredEndpoint.getId());

        assertEquals("http://example.com/new", retrievedMonitoredEndpoint.getUrl());
        assertEquals("test", retrievedMonitoredEndpoint.getName());
        assertEquals(3, retrievedMonitoredEndpoint.getMonitoringIntervalInSeconds());
        assertEquals(Instant.parse("2023-07-24T07:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfCreation());
        assertEquals(Instant.parse("2023-08-24T08:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfLastCheck());
        assertEquals(null, retrievedMonitoredEndpoint.getMonitoredEndpointResults());
    }

    @Test
    public void updatedMonitoredEndpoint_returnsExpectedCreatedEndpoint_whenProvidedOnlyMonitoredInterval() throws MonitoredEndpointNotFound {
        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint(
                null,
                "test",
                "http://example.com",
                UUID.fromString("0000486-4e71-42ad-bff3-d6f587f7a000"),
                Instant.parse("2023-07-24T07:19:18.695858456Z"),
                Instant.parse("2023-08-24T08:19:18.695858456Z"),
                3,
                null
        );
        MonitoredEndpointUpdates updates = new MonitoredEndpointUpdates(
                null,
                null,
                4
        );
        MonitoredEndpoint savedMonitoredEndpoint = service.updateMonitoredEndpoint(monitoredEndpoint, updates);

        MonitoredEndpoint retrievedMonitoredEndpoint = service.getMonitoredEndpoint(savedMonitoredEndpoint.getId());

        assertEquals("http://example.com", retrievedMonitoredEndpoint.getUrl());
        assertEquals("test", retrievedMonitoredEndpoint.getName());
        assertEquals(4, retrievedMonitoredEndpoint.getMonitoringIntervalInSeconds());
        assertEquals(Instant.parse("2023-07-24T07:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfCreation());
        assertEquals(Instant.parse("2023-08-24T08:19:18.695858456Z"), retrievedMonitoredEndpoint.getDateOfLastCheck());
        assertEquals(null, retrievedMonitoredEndpoint.getMonitoredEndpointResults());
    }

    @Test
    public void gettingMonitoredEndpointsOfUserWithId_returnsExpectedCreatedEndpoints() throws MonitoredEndpointNotFound {
        MonitoredEndpointRegistration monitoredEndpointRegistration1 = new MonitoredEndpointRegistration(
                "test",
                "http://example.com",
                1
        );
        MonitoredEndpointRegistration monitoredEndpointRegistration2 = new MonitoredEndpointRegistration(
                "test2",
                "http://example2.com",
                2
        );
        MonitoredEndpointRegistration monitoredEndpointRegistrationOfOtherUser = new MonitoredEndpointRegistration(
                "test3",
                "http://example3.com",
                3
        );
        UUID ownerId = UUID.fromString("185bb486-4e71-42ad-bff3-d6f587f7a548");
        UUID otherUserId = UUID.fromString("285bb486-4e71-42ad-bff3-d6f587f7a548");
        UUID storedMonitoredEndpointId1 = service.createMonitoredEndpoint(monitoredEndpointRegistration1, ownerId);
        UUID storedMonitoredEndpointId2 = service.createMonitoredEndpoint(monitoredEndpointRegistration2, ownerId);
        service.createMonitoredEndpoint(monitoredEndpointRegistrationOfOtherUser, otherUserId);
        List<MonitoredEndpoint> retrievedMonitoredEndpoints = service.getMonitoredEndpointsOfUserWithId(ownerId);

        assertEquals(2, retrievedMonitoredEndpoints.size());

        MonitoredEndpoint expectedMonitoredEndpoint1 = new MonitoredEndpoint(
                storedMonitoredEndpointId1,
                "test",
                "http://example.com",
                UUID.fromString("185bb486-4e71-42ad-bff3-d6f587f7a548"),
                Instant.parse("2023-08-24T08:19:18.695858456Z"),
                null,
                1,
                null
        );

        MonitoredEndpoint expectedMonitoredEndpoint2 = new MonitoredEndpoint(
                storedMonitoredEndpointId2,
                "test2",
                "http://example2.com",
                UUID.fromString("185bb486-4e71-42ad-bff3-d6f587f7a548"),
                Instant.parse("2023-08-24T08:19:18.695858456Z"),
                null,
                2,
                null
        );

        if(retrievedMonitoredEndpoints.get(0).getId().equals(storedMonitoredEndpointId1)){
            assertEquals(expectedMonitoredEndpoint1, retrievedMonitoredEndpoints.get(0));
            assertEquals(expectedMonitoredEndpoint2, retrievedMonitoredEndpoints.get(1));
        } else {
            assertEquals(expectedMonitoredEndpoint2, retrievedMonitoredEndpoints.get(0));
            assertEquals(expectedMonitoredEndpoint1, retrievedMonitoredEndpoints.get(1));
        }



    }

}

