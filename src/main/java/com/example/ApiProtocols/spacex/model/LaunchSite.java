package com.example.ApiProtocols.spacex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LaunchSite {
    @JsonProperty("site_name_long")
    private String siteNameLong;
}
