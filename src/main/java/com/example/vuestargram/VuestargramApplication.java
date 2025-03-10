package com.example.vuestargram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class VuestargramApplication {

    public static void main(String[] args) {
        SpringApplication.run(VuestargramApplication.class, args);
    }

}
