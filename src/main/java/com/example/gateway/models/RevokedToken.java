package com.example.gateway.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table
public class RevokedToken {
    @Id
    @Column(name = "revoked_id", nullable = false)
    private UUID revokedId;

    public UUID getRevokedId() {
        return revokedId;
    }

    public void setRevokedId(UUID revokedId) {
        this.revokedId = revokedId;
    }

    public RevokedToken() {
    }

    public RevokedToken(UUID uuid){
        this.revokedId = uuid;
    }

}