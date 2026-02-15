package com.mctait.api.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI taskApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management API")
                        .description("Spring Boot REST API for CRUD operations on Tasks"));
    }
}