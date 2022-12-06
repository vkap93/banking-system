package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Checking extends Account{

    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
    private String secretKey;

    public Checking() {
    }

    public Checking(AccountHolder primaryOwner) {
        super(primaryOwner);
    }

    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
