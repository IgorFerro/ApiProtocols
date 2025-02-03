package com.example.ApiProtocols.protocols.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spacex")
@Data
public class SpaceXConfig {
    private String apiUrl;
}