package com.example.gateway.repo;

import com.example.gateway.models.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RevokedTokenRepo extends JpaRepository<RevokedToken, UUID> {
}
