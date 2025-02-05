package com.example.ApiProtocols.protocols.service.Soap;

import com.example.ApiProtocols.protocols.model.soap.CountryFlagRequest;
import com.example.ApiProtocols.protocols.model.soap.CountryFlagResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/*
* Request → CountryService
  ↓
1. Create HTTP Headers
  ↓
2. Build SOAP Request
  ↓
3. Send Request via RestTemplate
  ↓
4. Extract Flag URL
  ↓
5. Build Response
  ↓
Response
* */

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {
    private final RestTemplate restTemplate;
    private static final String SOAP_URL = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";

    public CountryFlagResponse getCountryflag(CountryFlagRequest request){
        try {
            log.info("Fetching flag for country: {}", request.getCountryISOCode());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));

            String soapRequest = buildSoapRequest(request.getCountryISOCode());
            HttpEntity<String>entity = new HttpEntity<>(soapRequest, headers);

            String response = restTemplate.postForObject(SOAP_URL, entity, String.class);
            String flagUrl = extractFlagUrl(response);

            log.info("Retrieved flag URL: {}", flagUrl);

            return CountryFlagResponse.builder()
                    .flagUrl(flagUrl)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching country flag for {}", request.getCountryISOCode(), e);
            throw new RuntimeException("Error fetching country flag", e);
        }
    }

    private String buildSoapRequest(String countryCode) {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <CountryFlag xmlns="http://www.oorsprong.org/websamples.countryinfo">
                  <sCountryISOCode>%s</sCountryISOCode>
                </CountryFlag>
              </soap:Body>
            </soap:Envelope>
            """.formatted(countryCode);
    }

    private String extractFlagUrl(String soapResponse) {
        try {
            int start = soapResponse.indexOf("<m:CountryFlagResult>") + "<m:CountryFlagResult>".length();
            int end = soapResponse.indexOf("</m:CountryFlagResult>");
            return soapResponse.substring(start, end);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting flag URL from response", e);
        }
    }

}



