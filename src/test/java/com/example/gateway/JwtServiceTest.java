package com.example.gateway;

import com.example.gateway.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    //    @Value("${jwt.secret}")
    String secret = "G+KbPeShVmYq3t6w9z$C&F)J@McQfTjW";

    public UUID uuid = UUID.fromString("23c453e4-749b-42bb-b5e1-ae7d7a5eba26");

    @Test
    void getRevocationId() {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder().claim("revocationId",uuid).signWith(key).compact();
        UUID result = jwtService.getRevocationId(token);
        assertEquals(result, uuid);
    }
}