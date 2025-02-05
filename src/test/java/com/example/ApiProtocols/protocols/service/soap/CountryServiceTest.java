package com.example.ApiProtocols.protocols.service.soap;

import com.example.ApiProtocols.protocols.model.soap.CountryFlagRequest;
import com.example.ApiProtocols.protocols.model.soap.CountryFlagResponse;
import com.example.ApiProtocols.protocols.service.Soap.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryService countryService;

    @Test
    void getCountryFlag_ValidCountryCode_ReturnsFlagUrl() {
        // Given
        String soapResponse = """
                <?xml version="1.0" encoding="utf-8"?>
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                    <soap:Body>
                        <m:CountryFlagResponse>
                            <m:CountryFlagResult>http://example.com/flag/US.jpg</m:CountryFlagResult>
                        </m:CountryFlagResponse>
                    </soap:Body>
                </soap:Envelope>
                """;

        when(restTemplate.postForObject(eq("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso"),
                any(HttpEntity.class), eq(String.class)))
                .thenReturn(soapResponse);

        CountryFlagRequest request = CountryFlagRequest.builder()
                .countryISOCode("US")
                .build();

        // When
        CountryFlagResponse response = countryService.getCountryflag(request);

        // Then
        assertNotNull(response);
        assertEquals("http://example.com/flag/US.jpg", response.getFlagUrl());
    }
}
