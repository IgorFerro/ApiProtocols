package com.example.ApiProtocols.spacex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Launch {
  @JsonProperty("mission_name")
  private String missionName;

  @JsonProperty("launch_date_local")
    private String launchDateLocal;

  @JsonProperty("launch_success")
    private Boolean launchSuccess;

  private String details;
  private LaunchSite launchSite;
  private Rocket rocket;

}
