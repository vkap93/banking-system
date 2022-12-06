package com.ironhack.BankingSystem.models;

import com.google.common.hash.Hashing;
import jakarta.persistence.Entity;

import java.nio.charset.StandardCharsets;

@Entity
public class ThirdParty extends User{

private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String username, String hashedKey) {
        super(username);
        setHashedKey(hashedKey);
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        String sha256hex = Hashing.sha256()
                .hashString(hashedKey, StandardCharsets.UTF_8)
                .toString();
        this.hashedKey = sha256hex;
    }
}
