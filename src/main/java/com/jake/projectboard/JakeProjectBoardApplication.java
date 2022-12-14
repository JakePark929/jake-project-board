package com.jake.projectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@SpringBootApplication
public class JakeProjectBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(JakeProjectBoardApplication.class, args);
    }

}
