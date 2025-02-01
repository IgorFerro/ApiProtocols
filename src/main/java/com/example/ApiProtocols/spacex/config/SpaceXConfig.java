package com.example.ApiProtocols.spacex.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix= "spacex")
@Data
public class SpaceXConfig {
    private String apiUrl = "https://api.spacex.land/graphql/";
}
