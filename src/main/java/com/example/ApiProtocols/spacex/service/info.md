Key Components of the Class
1. @Service Annotation
   Marks the class as a Spring service.
   This tells Spring that this class contains business logic and should be managed as a Spring bean.
   The SpaceXService class is responsible for interacting with the SpaceX GraphQL API and processing the data.
2. @Slf4j Annotation
   This is a Lombok annotation that provides a logger for the class.
   The log object can be used to log messages for debugging or monitoring purposes.
   Example usage:
   java
   Copy Code
   log.info("Fetching past launches with limit: {}", limit);
3. @RequiredArgsConstructor Annotation
   This is a Lombok annotation that generates a constructor for all final fields in the class.
   In this case, it generates a constructor for the spaceXConfig field, which is injected by Spring's dependency injection.
4. spaceXConfig Field
   This is a reference to the SpaceXConfig class, which provides the base URL of the SpaceX GraphQL API.
   The spaceXConfig bean is injected into the SpaceXService class using Spring's dependency injection.
   Methods in the Class
1. getPastLaunches(int limit)
   This method fetches a list of past SpaceX launches from the GraphQL API.
   Parameters:
   limit: The maximum number of launches to fetch.
   GraphQL Query:
   The query is dynamically constructed using the limit parameter.
   Example query:
   graphql
   Copy Code
   query {
   launchesPast(limit: 5) {
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
   Return Value:
   A list of Launch objects, each representing a past SpaceX launch.
   How It Works:
   The method constructs the GraphQL query as a string.
   It calls the executeQuery method to send the query to the SpaceX API and retrieve the results.
2. getLaunch(String id)
   This method fetches details about a specific SpaceX launch by its ID.
   Parameters:
   id: The ID of the launch to fetch.
   GraphQL Query:
   The query is dynamically constructed using the id parameter.
   Example query:
   graphql
   Copy Code
   query {
   launch(id: "108") {
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
   Return Value:
   A Launch object representing the details of the specified launch.
   How It Works:
   The method constructs the GraphQL query as a string.
   It calls the executeQuery method to send the query to the SpaceX API and retrieve the results.
3. executeQuery(String query, String path)
   This is a private helper method that sends a GraphQL query to the SpaceX API and processes the response.
   Parameters:
   query: The GraphQL query to execute.
   path: The JSON path to extract the desired data from the API response.
   How It Works:
   A JSONObject is created to represent the GraphQL query.
   java
   Copy Code
   JSONObject jsonObject = new JSONObject();
   jsonObject.put("query", query);
   The query is sent to the SpaceX API using Rest Assured:
   java
   Copy Code
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
   The post method sends the query to the API URL provided by spaceXConfig.getApiUrl().
   The response is validated to ensure the status code is 200.
   The jsonPath() method is used to extract the desired data from the response.
   The extracted data is returned as an object of the specified type (T).
   Example Workflow
   Fetching Past Launches:
   The controller calls the getPastLaunches method with a limit parameter.
   The getPastLaunches method constructs a GraphQL query to fetch the past launches.
   The query is sent to the SpaceX API using the executeQuery method.
   The response is deserialized into a list of Launch objects and returned to the controller.
   Fetching a Specific Launch:
   The controller calls the getLaunch method with a launch ID.
   The getLaunch method constructs a GraphQL query to fetch the details of the specified launch.
   The query is sent to the SpaceX API using the executeQuery method.
   The response is deserialized into a Launch object and returned to the controller.
   Example API Response and Mapping
   Example JSON Response for getPastLaunches:
   json
   Copy Code
   {
   "data": {
   "launchesPast": [
   {
   "mission_name": "FalconSat",
   "launch_date_local": "2006-03-24T22:30:00-04:00",
   "launch_success": false,
   "details": "Engine failure at 33 seconds and loss of vehicle",
   "launch_site": {
   "site_name_long": "Kwajalein Atoll Omelek Island"
   },
   "rocket": {
   "rocket_name": "Falcon 1",
   "rocket_type": "Merlin A",
   "fairings": {
   "reused": false,
   "recovery_attempt": false,
   "recovered": false
   }
   }
   }
   ]
   }
   }
   This JSON response is deserialized into a list of Launch objects, with nested objects for LaunchSite, Rocket, and Fairings.

Advantages of SpaceXService
Encapsulation:
The service encapsulates the logic for interacting with the SpaceX API, keeping the controller layer clean and focused on handling HTTP requests.
Reusability:
The methods in the service can be reused across different parts of the application.
Separation of Concerns:
The service layer separates the business logic (e.g., constructing queries, processing responses) from the controller layer.
Dynamic Queries:
The service dynamically constructs GraphQL queries based on input parameters (e.g., limit, id).
Error Handling:
The executeQuery method validates the API response (e.g., checking for a 200 status code) and ensures that only valid data is returned.
Summary
The SpaceXService class is the core of the service layer. It:

Interacts with the SpaceX GraphQL API.
Dynamically constructs GraphQL queries.
Processes API responses and maps them to Java objects.
Provides methods for fetching past launches and specific launch details.
Uses Spring's dependency injection to access configuration properties (SpaceXConfig).
Keeps the controller layer clean by encapsulating the API interaction logic.