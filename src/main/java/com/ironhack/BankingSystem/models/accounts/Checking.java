package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.Random;

@Entity
public class Checking extends Account{

    private final BigDecimal minimumBalance = BigDecimal.valueOf(250);
    private final BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12);
    private final BigDecimal penaltyFee = BigDecimal.valueOf(40);
    private String secretKey;

    public Checking() {
    }

    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
        this.secretKey = String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    @Override
    public void updateBalance(BigDecimal balanceChange) {
        super.updateBalance(balanceChange);
        if (getBalance().compareTo(minimumBalance) < 0) {
            setBalance(getBalance().subtract(getPenaltyFee()));
        }
    }
}
