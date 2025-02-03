package com.example.ApiProtocols.protocols.service;

import com.example.ApiProtocols.protocols.config.SpaceXConfig;
import com.example.ApiProtocols.protocols.model.Launch;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpaceXService {

    private final SpaceXConfig spaceXConfig;

    public List<Launch> getPastLaunches(int limit) {
        log.info("Fetching past launches with limit: {}", limit);
        String query = """
                query {
                    launchesPast(limit: %d) {
                        mission_name
                        launch_date_local
                        launch_success
                        details
                        launch_site {
                            site_name_long
                        }
                        rocket {
                            rocket_name
                            rocket_type
                            fairings {
                                reused
                                recovery_attempt
                                recovered
                            }
                        }
                    }
                }
                """.formatted(limit);

        List<Launch> launches = executeQuery(query, "launchesPast");
        log.info("Successfully retrieved {} past launches", launches != null ? launches.size() : 0);
        return launches;
    }

    public Launch getLaunch(String id) {
        log.info("Fetching launch details for ID: {}", id);
        String query = """
                query {
                    launch(id: "%s") {
                        mission_name
                        launch_date_local
                        launch_success
                        details
                        launch_site {
                            site_name_long
                        }
                        rocket {
                            rocket_name
                            rocket_type
                            fairings {
                                reused
                                recovery_attempt
                                recovered
                            }
                        }
                    }
                }
                """.formatted(id);

        Launch launch = executeQuery(query, "launch");
        log.info("Launch details retrieved for ID: {}", id);
        return launch;
    }

    private <T> T executeQuery(String query, String path) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("query", query);
            requestBody.put("variables", new JSONObject());

            log.debug("Executing GraphQL query to SpaceX API: {}", requestBody);
            log.debug("API URL: {}", spaceXConfig.getApiUrl());

            long startTime = System.currentTimeMillis();

            Response response = given()
                    .relaxedHTTPSValidation()
                    .contentType(ContentType.JSON)
                    .body(requestBody.toString())
                    .when()
                    .post(spaceXConfig.getApiUrl());

            long endTime = System.currentTimeMillis();
            log.debug("API response time: {}ms", endTime - startTime);

            if (response.getStatusCode() != 200) {
                log.error("API request failed with status code: {}", response.getStatusCode());
                log.error("Response body: {}", response.getBody().asString());
                throw new RuntimeException("API request failed with status code: " + response.getStatusCode());
            }

            T result = response.then()
                    .log().ifValidationFails()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .getObject("data." + path, (Class<T>) Object.class);

            log.debug("Successfully executed GraphQL query. Response size: {} bytes",
                    response.getBody().asByteArray().length);

            return result;

        } catch (Exception e) {
            log.error("Error executing GraphQL query to SpaceX API: {}", e.getMessage(), e);
            log.error("Query details: {}", query);
            throw new RuntimeException("Failed to execute SpaceX API query", e);
        }
    }
}