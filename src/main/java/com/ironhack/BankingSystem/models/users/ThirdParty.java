package com.ironhack.BankingSystem.models.users;

import com.google.common.hash.Hashing;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.nio.charset.StandardCharsets;

@Entity
public class ThirdParty {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotNull
private String name;
private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String name, String hashedKey) {
        setName(name);
        setHashedKey(hashedKey);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        String sha256hex = Hashing.sha256()
                .hashString(hashedKey + name, StandardCharsets.UTF_8)
                .toString();
        this.hashedKey = sha256hex;
    }
}
