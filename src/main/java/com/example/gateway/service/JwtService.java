package com.example.gateway.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.UUID;

@Service
public class JwtService {

    //    @Value("${jwt.secret}")
    String secret = "G+KbPeShVmYq3t6w9z$C&F)J@McQfTjW";

    public Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public UUID getRevocationId(String jwt) {
        try {
            String strRevId = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt).getBody().get("revocationId", String.class);
            return UUID.fromString(strRevId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    }
