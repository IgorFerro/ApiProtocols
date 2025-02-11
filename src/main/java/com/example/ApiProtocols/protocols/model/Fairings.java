package com.example.ApiProtocols.protocols.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Fairings {
    private Boolean reused;

    @JsonProperty("recovery_attempt")
    private Boolean recoveryAttempt;

    private Boolean recovered;
}
