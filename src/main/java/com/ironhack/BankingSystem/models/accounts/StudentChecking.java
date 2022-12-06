package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

@Entity
public class StudentChecking extends Account {

    private String secretKey;
    public StudentChecking() {
    }

    public StudentChecking(AccountHolder primaryOwner) {
        super(primaryOwner);
    }

    public StudentChecking(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


}
