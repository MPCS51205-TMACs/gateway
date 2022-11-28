package com.example.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {
    String userService = "http://user-service:8080";
    String watchlistService = "http://watchlist-service:8080";
    String notificationService = "http://notification-service:8080";

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // use regex to map to microservice
                // USER
                .route(p -> p
                        .path("/register","/login","/user/**")
                        .uri(userService))

                // WATCHLIST
                .route(p -> p
                        .path("/watchlist")
                        .uri(watchlistService))
            
                // NOTIFICATION
                .route(p -> p
                        .path("/email", "/email/*", "/email/inbox/*", "/email/outbox/*", "/email/template/*", "/user-profile/*", "/user-profile","/notification/api/v1/**")
                        .uri(notificationService))
            

                .build();
    }


}
