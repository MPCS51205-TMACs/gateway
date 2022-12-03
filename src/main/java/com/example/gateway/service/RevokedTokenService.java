package com.example.gateway.service;

import com.example.gateway.models.RevokedToken;
import com.example.gateway.repo.RevokedTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class RevokedTokenService {
    @Autowired
    RevokedTokenRepo revokedTokenRepo;

    @Autowired
    JwtService jwtService;

    public RevokedToken save(UUID revokedId){
        return revokedTokenRepo.save(new RevokedToken(revokedId));
    }

    private boolean has(UUID revokedId){
        return  revokedTokenRepo.findById(revokedId).isPresent();
    }

    public boolean authorizationIsRevoked(ServerHttpRequest http){
        String authorizationHeader = http.getHeaders().getFirst("Authorization");
        if (authorizationHeader==null || !authorizationHeader.contains("Bearer ")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String token = authorizationHeader.substring(7);
        UUID revocationId = jwtService.getRevocationId(token);
        if (has(revocationId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return false;
    }

}
