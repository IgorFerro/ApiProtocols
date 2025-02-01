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

/*
* The SpaceXService.java class is part of the service layer in the project.
* It is responsible for interacting with the SpaceX GraphQL API, executing queries, and returning the results to the controller layer.
* This class encapsulates the logic for making HTTP requests to the SpaceX API and processing the responses.
* */

@Service
@Slf4j
@RequiredArgsConstructor
public class SpaceXService {

    private final SpaceXConfig spaceXConfig;

    public List<Launch> getPastLaunches(int limit){
        String query = """
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
        return executeQuery(query, "launchedPast");
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

    private <T> T executeQuery(String query, String path){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", query);

        return given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post(spaceXConfig.getApiUrl())
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getObject("data." + path, (Class<T>) Object.class);
    }
}
