package com.example.gateway.routes;

import com.example.gateway.filter.RevokedTokenFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Routes {

    String userService = "http://user-service:8080";
    String auctionsService = "http://auctions-service:10000";
    String camService = "http://cam-service:51224";
    String watchlistService = "http://watchlist-service:8080";
    String notificationService = "http://notification-service:8080";
    String itemService = "http://item-service:8088";
    String cartService = "http://shopping-cart-service:10001";

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, RevokedTokenFilterFactory filterFactory) {
        return builder.routes()
                // use regex to map to microservice
                // REGISTRATION AND LOGIN
                .route(p -> p.path("/register", "/login").uri(userService))

                // USER
                .route(p -> p
                        .path("/user/**")
                        .filters(f-> f.filter(filterFactory.apply()))
                        .uri(userService)
                )


                // WATCHLIST
                .route(p -> p
                        .path("/watchlist")
                        .filters(f-> f.filter(filterFactory.apply()))
                        .uri(watchlistService))

                // NOTIFICATION
                .route(p -> p
                        .path("/email**", "/email/**", "/user-profile/*", "/user-profile", "/notification/api/v1/**")
                        .filters(f-> f.filter(filterFactory.apply()))
                        .uri(notificationService))

                // AUCTIONS
                .route(p -> p
                        .path("/api/v1/**", "/api/v1/Bids/**", "/api/v1/ItemsUserHasBidsOn/**", "/api/v1/cancelAuction/**", "/api/v1/stopAuction/**", "/api/v1/activeAuctions/**")
                        .filters(f-> f.filter(filterFactory.apply()))
                        .uri(auctionsService))

                // AUCTIONS
                .route(p -> p
                        .path("/api/v1/closedauctions/**")
                        .filters(f-> f.filter(filterFactory.apply()))
                        .uri(camService))

                // ITEM
                .route(p -> p
                        .path("/item", "/item/**","/category/**")
                        .filters(f-> f.filter(filterFactory.apply()))
                        .uri(itemService))


                // SHOPPING-CART
                .route(p -> p
                        .path("/carts/**","/boughtitems/**","/receipts/*")
                        .filters(f-> f.filter(filterFactory.apply()))
                        .uri(cartService))

                .build();
    }
}
