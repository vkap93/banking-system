package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class CreditCard extends Account {

    private BigDecimal creditLimit;

    private BigDecimal interestRate;

    public CreditCard(AccountHolder primaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard(AccountHolder primaryOwner) {
        super(primaryOwner);
    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
