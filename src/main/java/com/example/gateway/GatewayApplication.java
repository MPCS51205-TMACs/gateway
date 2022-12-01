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
    String auctionsService = "http://auctions-service:10000";
    String camService = "http://cam-service:51224";
    String watchlistService = "http://watchlist-service:8080";
    String notificationService = "http://notification-service:8080";
    String itemService = "http://item-service:8088";
    String cartService = "http://shopping-cart-service:10001";

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

                // AUCTIONS
                .route(p -> p
                        .path("/api/v1/Auctions/**","/api/v1/Bids/**","/api/v1/ItemsUserHasBidsOn/**","/api/v1/cancelAuction/**","/api/v1/stopAuction/**","/api/v1/activeAuctions/**")
                        .uri(auctionsService))

                // CLOSED-AUCTION-METRICS (CAM)
                .route(p -> p
                        .path("/api/v1/closedauctions/**")
                        .uri(camService))

                // ITEM
                .route(p -> p
                        .path("/item/**")
                        .uri(itemService))

                // SHOPPING-CART
                        .route(p -> p
                        .path("/carts/**","/boughtitems/**","/receipts/**")
                        .uri(cartService))
            

                .build();
    }


}
