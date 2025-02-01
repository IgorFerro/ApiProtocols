package com.example.ApiProtocols.spacex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class Rocket {
    @JsonProperty("rocket_name")
    private String rocketName;

    @JsonProperty("rocket_type")
    private String rocket_type;

    private Fairings fairings;
    private Height height;
    private Mass mass;

    @JsonProperty("first_flight")
    private String firstFlight;

    private boolean active;

}
