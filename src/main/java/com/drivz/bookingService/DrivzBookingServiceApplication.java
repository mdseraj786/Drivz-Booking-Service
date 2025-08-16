package com.drivz.bookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EntityScan("com.drivz.commonLibrary")
@EnableDiscoveryClient
public class DrivzBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivzBookingServiceApplication.class, args);
    }

}
