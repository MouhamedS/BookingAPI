package com.kata.Booking.API.application.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("reservation-api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI bankAccountOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Reservation API")
                        .description("This is the core API for Room availability and reservation booking in a specified database. Inventory searches, reservation searches, \" +\n" +
                                "                        \"inventory creations, reservation bookings, inventory deletions and reservation deletions can be made.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
