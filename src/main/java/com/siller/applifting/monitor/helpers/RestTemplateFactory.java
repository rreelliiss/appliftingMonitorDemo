package com.siller.applifting.monitor.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {

    public RestTemplate newRestTemplate() {
        return new RestTemplate();
    }

}
