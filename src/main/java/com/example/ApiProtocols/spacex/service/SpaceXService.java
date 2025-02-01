package com.example.ApiProtocols.spacex.service;

import com.example.ApiProtocols.spacex.config.SpaceXConfig;
import com.example.ApiProtocols.spacex.model.Launch;
import io.restassured.http.ContentType;
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
        return executeQuery(query, "launchesPast");
    }

    public Launch getLaunch(String id) {
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

        return executeQuery(query, "launch");
    }

    private <T> T executeQuery(String query, String path) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("query", query);
            requestBody.put("variables", new JSONObject());

            log.debug("Executing GraphQL query to SpaceX API: {}", requestBody);

            return given()
                    .relaxedHTTPSValidation()
                    .contentType(ContentType.JSON)
                    .body(requestBody.toString())
                    .when()
                    .post(spaceXConfig.getApiUrl())
                    .then()
                    .log().ifValidationFails()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .getObject("data." + path, (Class<T>) Object.class);

        } catch (Exception e) {
            log.error("Error executing GraphQL query to SpaceX API: {}", e.getMessage());
            throw new RuntimeException("Failed to execute SpaceX API query", e);
        }
    }
}