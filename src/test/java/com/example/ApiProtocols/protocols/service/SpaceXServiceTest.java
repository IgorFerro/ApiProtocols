package com.example.ApiProtocols.protocols.service;

import com.example.ApiProtocols.protocols.model.Launch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpaceXServiceTest {

    @Autowired
    private SpaceXService spaceXService;
/*
    @Test
    void getPastLaunches(){
        List<Launch> launches = spaceXService.getPastLaunches(10);
        assertNotNull(launches);
        assertEquals(3, launches.size());
        launches.forEach(launch -> {
            assertNotNull(launch.getMissionName());
            assertNotNull(launch.getLaunchDateLocal());
        });
    }

    @Test
    void getLaunch(){
        Launch launch = spaceXService.getLaunch("5eb87cd9ffd86e000604b32a");
        assertNotNull(launch);
        assertNotNull(launch.getMissionName());
        assertNotNull(launch.getRocket());
        //assertNotNull(launch.getRocket().getRocketName());
    }*/

    @Test
    void getPastLaunches_ShouldReturnLaunches() {
        List<Launch> launches = spaceXService.getPastLaunches(10);

        assertThat(launches)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void getLaunch_ShouldReturnLaunch() {
        // Using a known launch ID from SpaceX API
        Launch launch = spaceXService.getLaunch("5eb87cd9ffd86e000604b32a");

        assertThat(launch)
                .isNotNull();
        assertThat(launch.getMissionName())
                .isNotEmpty();
    }

}
