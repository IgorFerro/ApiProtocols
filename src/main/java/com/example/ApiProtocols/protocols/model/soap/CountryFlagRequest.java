package com.example.ApiProtocols.protocols.model.soap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryFlagRequest {
    @NotBlank(message = "Country ISO code is required")
    @Size(min = 2, max = 2, message = "Country ISO code must be exactly 2 characters")
    private String countryISOCode;
}
