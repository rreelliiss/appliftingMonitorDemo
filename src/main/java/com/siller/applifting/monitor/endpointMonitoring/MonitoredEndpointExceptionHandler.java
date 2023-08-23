package com.siller.applifting.monitor.endpointMonitoring;

import com.siller.applifting.monitor.endpointMonitoring.service.MonitoredEndpointNotFound;
import com.siller.applifting.monitor.security.NotAuthenticated;
import com.siller.applifting.monitor.security.NotAuthorized;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MonitoredEndpointExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MonitoredEndpointNotFound.class)
    public void handleNotFound() {}

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthenticated.class)
    public void handleNotAuthenticated() {}

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotAuthorized.class)
    public void handleNotAuthorized() {}


}
