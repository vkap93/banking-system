package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class CreditCard extends Account {

    private BigDecimal creditLimit;


    private BigDecimal interestRate;


    public CreditCard() {
    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }


    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (interestRate == null) {
            this.interestRate = BigDecimal.valueOf(100);
        } else if (interestRate.compareTo(BigDecimal.valueOf(100)) < 0 || interestRate.compareTo(BigDecimal.valueOf(100000)) > 0) {
            throw new IllegalArgumentException("The credit limit should be from 100 and 100000");
        } else {
            this.creditLimit = creditLimit;
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate == null) {
            this.interestRate = BigDecimal.valueOf(0.2);
        } else if (interestRate.compareTo(BigDecimal.valueOf(0.1)) < 0 || interestRate.compareTo(BigDecimal.valueOf(0.2)) > 0) {
            throw new IllegalArgumentException("The interest rate should be between 0.1 and 0.2");
        } else {
            this.interestRate = interestRate;
        }
    }
}
