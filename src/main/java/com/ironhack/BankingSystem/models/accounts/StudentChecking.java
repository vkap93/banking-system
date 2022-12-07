package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.util.Random;

@Entity
public class StudentChecking extends Account {

    private String secretKey;
    public StudentChecking() {
    }

    public StudentChecking(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
        this.secretKey = String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


}
