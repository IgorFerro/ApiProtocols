package com.example.ApiProtocols.spacex.controller;

import com.example.ApiProtocols.spacex.model.Launch;
import com.example.ApiProtocols.spacex.service.SpaceXService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
The SpaceXController.java class is part of the controller layer in the project.
It is responsible for handling HTTP requests from clients, invoking the appropriate service methods, and returning the results as HTTP responses.
This class acts as the entry point for the SpaceX API-related operations in the application.
 */

@RestController
@RequestMapping("/api/spacex")
@RequiredArgsConstructor
public class SpaceXController {

    private  final SpaceXService spaceXService;

    @GetMapping("/launches/past")
    public ResponseEntity<List<Launch>> getPastLaunches(@RequestParam(defaultValue = "5") int limit) {
    return ResponseEntity.ok(spaceXService.getPastLaunches(limit));
    }

    @GetMapping("/launches/{id}")
    public ResponseEntity<Launch> getLaunch(@PathVariable String id){
        return ResponseEntity.ok(spaceXService.getLaunch(id));
    }
}
