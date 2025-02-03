package com.example.ApiProtocols.protocols.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SoapConfig {

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
