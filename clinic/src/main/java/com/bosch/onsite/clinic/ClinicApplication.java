package com.bosch.onsite.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClinicApplication {

    public static void main(String... args) {
        SpringApplication.run(ClinicApplication.class, args);
    }
}
