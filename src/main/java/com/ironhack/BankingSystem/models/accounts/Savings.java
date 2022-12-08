package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.Random;

@Entity
public class Savings extends Account {
    private String secretKey = String.valueOf(new Random().nextInt(900000) + 100000);

    private final BigDecimal penaltyFee = BigDecimal.valueOf(40);
    private BigDecimal interestRate;
    private BigDecimal minimumBalance;

    public Savings() {
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate, BigDecimal minimumBalance) {
        super(primaryOwner, secondaryOwner);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
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
        if (interestRate == null) {
            this.interestRate = BigDecimal.valueOf(0.0025);
        } else if (interestRate.compareTo(BigDecimal.valueOf(0.0025)) < 0 || interestRate.compareTo(BigDecimal.valueOf(0.5)) > 0) {
            throw new IllegalArgumentException("The interest rate should be between 0.0025 and 0.5");
        } else {
            this.interestRate = interestRate;
        }
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance == null) {
            this.minimumBalance = BigDecimal.valueOf(1000);
        } else if (minimumBalance.compareTo(BigDecimal.valueOf(100)) < 0 || interestRate.compareTo(BigDecimal.valueOf(1000)) > 0) {
            throw new IllegalArgumentException("The minimum balance should be between 100 and 1000");
        } else {
            this.minimumBalance = minimumBalance;
        }
    }

    public void updateBalance(BigDecimal balanceChange) {
        setBalance(getBalance().add(balanceChange));
        if (getBalance().compareTo(minimumBalance) < 0) {
            setBalance(getBalance().subtract(getPenaltyFee()));
        }
    }
}
