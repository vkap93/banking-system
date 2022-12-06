package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Savings extends Account {
    private String secretKey;
    private BigDecimal interestRate;
    private BigDecimal minimumBalance;

    public Savings() {
    }

    public Savings(AccountHolder primaryOwner, BigDecimal interestRate, BigDecimal minimumBalance) {
        super(primaryOwner);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate, BigDecimal minimumBalance) {
        super(primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    public Savings(AccountHolder primaryOwner) {
        super(primaryOwner);
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
