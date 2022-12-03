package com.example.gateway.filter;

import com.example.gateway.service.RevokedTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RevokedTokenFilterFactory extends
        AbstractGatewayFilterFactory<RevokedTokenFilterFactory.Config> {

    @Autowired
    RevokedTokenService revokedTokenService;

    public RevokedTokenFilterFactory() {
        super(Config.class);
    }

    //https://www.baeldung.com/spring-cloud-custom-gateway-filters
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
            ServerHttpRequest http = exchange.getRequest();
            boolean isRevoked = revokedTokenService.authorizationIsRevoked(http);
            if (isRevoked){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };

    }

    public GatewayFilter apply() {
        return this.apply(new Config());

    }


    public static class Config {
    }
}