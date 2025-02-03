Key Components of the Class
1. @RestController Annotation
   Marks the class as a REST controller in Spring Boot.
   Combines @Controller and @ResponseBody, meaning all methods in this class return data directly as JSON or XML (instead of rendering a view).
   This makes it ideal for building RESTful APIs.
2. @RequiredArgsConstructor Annotation
   This is a Lombok annotation that generates a constructor for all final fields in the class.
   In this case, it generates a constructor for the spaceXService field, which is injected by Spring's dependency injection.
3. spaceXService Field
   This is a reference to the SpaceXService class, which contains the business logic for interacting with the SpaceX GraphQL API.
   The spaceXService bean is injected into the SpaceXController class using Spring's dependency injection.
   Methods in the Class
1. getPastLaunches(int limit)
   Endpoint: /launches/past
   HTTP Method: GET
   Parameters:
   limit (optional): The maximum number of past launches to fetch. Defaults to 5 if not provided.
   Return Value:
   A list of Launch objects, each representing a past SpaceX launch.
   How It Works:
   The method calls the getPastLaunches method in the SpaceXService class, passing the limit parameter.
   The service fetches the data from the SpaceX GraphQL API and returns it as a list of Launch objects.
   The controller returns this list as the HTTP response in JSON format.
   Example Request:

http
Copy Code
GET /launches/past?limit=3
Example Response:

json
Copy Code
[
{
"missionName": "FalconSat",
"launchDateLocal": "2006-03-24T22:30:00-04:00",
"launchSuccess": false,
"details": "Engine failure at 33 seconds and loss of vehicle",
"launchSite": {
"siteNameLong": "Kwajalein Atoll Omelek Island"
},
"rocket": {
"rocketName": "Falcon 1",
"rocketType": "Merlin A",
"fairings": {
"reused": false,
"recoveryAttempt": false,
"recovered": false
}
}
},
...
]
2. getLaunchById(String id)
   Endpoint: /launch/{id}
   HTTP Method: GET
   Parameters:
   id: The ID of the launch to fetch.
   Return Value:
   A Launch object representing the details of the specified launch.
   How It Works:
   The method calls the getLaunch method in the SpaceXService class, passing the id parameter.
   The service fetches the data from the SpaceX GraphQL API and returns it as a Launch object.
   The controller returns this object as the HTTP response in JSON format.
   Example Request:

http
Copy Code
GET /launch/108
Example Response:

json
Copy Code
{
"missionName": "FalconSat",
"launchDateLocal": "2006-03-24T22:30:00-04:00",
"launchSuccess": false,
"details": "Engine failure at 33 seconds and loss of vehicle",
"launchSite": {
"siteNameLong": "Kwajalein Atoll Omelek Island"
},
"rocket": {
"rocketName": "Falcon 1",
"rocketType": "Merlin A",
"fairings": {
"reused": false,
"recoveryAttempt": false,
"recovered": false
}
}
}
Key Annotations in the Methods
1. @GetMapping
   Maps HTTP GET requests to the specified endpoint.
   Example:
   java
   Copy Code
   @GetMapping("/launches/past")
   public List<Launch> getPastLaunches(@RequestParam(defaultValue = "5") int limit) {
   return spaceXService.getPastLaunches(limit);
   }
   This maps GET /launches/past requests to the getPastLaunches method.
2. @RequestParam
   Used to extract query parameters from the HTTP request.
   Example:
   java
   Copy Code
   @RequestParam(defaultValue = "5") int limit
   This extracts the limit query parameter from the request URL (e.g., /launches/past?limit=3).
   If the parameter is not provided, the default value (5) is used.
3. @PathVariable
   Used to extract path variables from the URL.
   Example:
   java
   Copy Code
   @PathVariable String id
   This extracts the id path variable from the request URL (e.g., /launch/108).
   Example Workflow
   Fetching Past Launches:
   A client sends a GET request to /launches/past with an optional limit query parameter.
   The getPastLaunches method in the controller is invoked.
   The controller calls the getPastLaunches method in the SpaceXService class, passing the limit parameter.
   The service fetches the data from the SpaceX GraphQL API and returns it as a list of Launch objects.
   The controller returns this list as the HTTP response in JSON format.
   Fetching a Specific Launch:
   A client sends a GET request to /launch/{id} with the id path variable.
   The getLaunchById method in the controller is invoked.
   The controller calls the getLaunch method in the SpaceXService class, passing the id parameter.
   The service fetches the data from the SpaceX GraphQL API and returns it as a Launch object.
   The controller returns this object as the HTTP response in JSON format.
   Advantages of SpaceXController
   Separation of Concerns:
   The controller handles HTTP requests and responses, while the service layer handles the business logic.
   Reusability:
   The controller methods can be reused for different clients (e.g., web apps, mobile apps, or other APIs).
   Dynamic Query Parameters:
   The use of @RequestParam and @PathVariable allows the controller to handle dynamic input from the client.
   RESTful Design:
   The controller follows RESTful principles, with clear and meaningful endpoints (/launches/past, /launch/{id}).
   JSON Responses:
   The @RestController annotation ensures that all responses are automatically serialized to JSON, making it easy for clients to consume the API.
   Summary
   The SpaceXController class is the entry point for handling HTTP requests related to the SpaceX API. It:

Exposes endpoints for fetching past launches and specific launch details.
Delegates the business logic to the SpaceXService class.
Uses Spring annotations like @RestController, @GetMapping, @RequestParam, and @PathVariable to handle HTTP requests and parameters.
Returns data as JSON responses, making it easy for clients to consume the API.
This class ensures a clean separation of concerns, keeping the controller focused on request handling and delegating the business logic to the service layer.