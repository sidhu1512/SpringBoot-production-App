package com.example.fitness.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fitness Tracking API")
                        .version("v1.0")
                        .description("Production Grade API's")
                        .contact(new Contact()
                                .name("Siddharth Bhadu")
                                .url("https://github.com/sidhu1512")
                                .email("SiddharthBhadu.work@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/sidhu1512")
                        )
                );
    }
}
