package com.test.obs.solution;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Test OBS Solution API",
                version = "1.0",
                description = "Test OBS Solution API"
        )
)
public class TestObsSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestObsSolutionApplication.class, args);
    }

}
