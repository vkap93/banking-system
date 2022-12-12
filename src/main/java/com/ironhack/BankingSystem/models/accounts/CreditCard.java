package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class CreditCard extends Account {


    private BigDecimal creditLimit;

    private BigDecimal interestRate;

    private LocalDate interestDate = getCreationDate().plusMonths(1);


    public CreditCard() {
    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner);
        setCreditLimit(creditLimit);
        setInterestDate(interestDate);
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

    public LocalDate getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(LocalDate interestDate) {
        this.interestDate = interestDate;
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

    public void applyInterest() {
        int monthsFromCreation = Period.between(getCreationDate(),LocalDate.now()).getMonths();
        if (monthsFromCreation >= 1 && getInterestDate() == getCreationDate().plusMonths(1)) {
            setBalance(super.getBalance().add(super.getBalance().multiply((getInterestRate().divide(BigDecimal.valueOf(12),2, RoundingMode.UP)).multiply(BigDecimal.valueOf(monthsFromCreation)))));
            setInterestDate(LocalDate.now());
        } else if (Period.between(interestDate, LocalDate.now()).getMonths() >=1 ) {
            int monthsFromInterest = Period.between(interestDate, LocalDate.now()).getMonths();
            setBalance(super.getBalance().add(super.getBalance().multiply((getInterestRate().divide(BigDecimal.valueOf(12),2, RoundingMode.UP)).multiply(BigDecimal.valueOf(monthsFromInterest)))));
            setInterestDate(LocalDate.now());
        }
    }

    @Override
    public BigDecimal getBalance() {
        applyInterest();
        setBalance(super.getBalance().add(creditLimit));
        return super.getBalance();
    }
}
