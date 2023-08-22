package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MonitoredEndpointExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MonitoredEndpointNotFound.class)
    public void handleNotFound() {}

}
