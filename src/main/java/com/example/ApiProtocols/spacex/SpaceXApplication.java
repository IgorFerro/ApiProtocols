package com.example.ApiProtocols.spacex;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix= "spacex")
@Data
public class SpaceXApplication {

    private String apiUrl = "https://api.spacex.land/graphql/";
}
