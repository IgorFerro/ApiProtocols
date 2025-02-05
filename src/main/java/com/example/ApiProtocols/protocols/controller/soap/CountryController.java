package com.example.ApiProtocols.protocols.controller.soap;

import com.example.ApiProtocols.protocols.model.soap.CountryFlagRequest;
import com.example.ApiProtocols.protocols.model.soap.CountryFlagResponse;
import com.example.ApiProtocols.protocols.service.Soap.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP Request → Controller
 *     ↓
 * Build Request Object
 *     ↓
 * Call Service
 *     ↓
 * Wrap Response in ResponseEntity
 *     ↓
 * Return to Client
 *
 */

@RestController
@RequestMapping("api/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/flag/{countryCode}")
    public ResponseEntity<CountryFlagResponse>getCountryFlag(@PathVariable String countryCode){
        CountryFlagRequest request = CountryFlagRequest.builder()
                .countryISOCode(countryCode.toUpperCase())
                .build();
                return ResponseEntity.ok(countryService.getCountryflag(request));
    }

}
