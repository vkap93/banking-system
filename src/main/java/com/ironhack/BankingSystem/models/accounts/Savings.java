package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

@Entity
public class Savings extends Account {

    private final BigDecimal penaltyFee = BigDecimal.valueOf(40);
    private BigDecimal interestRate;
    private BigDecimal minimumBalance;
    private LocalDate interestDate = getCreationDate().plusYears(1);

    public Savings() {
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate, BigDecimal minimumBalance) {
        super(primaryOwner, secondaryOwner);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
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

    @Override
    public void updateBalance(BigDecimal balanceChange) {
        super.updateBalance(balanceChange);
        if (getBalance().compareTo(minimumBalance) < 0) {
            setBalance(getBalance().subtract(getPenaltyFee()));
        }
    }

    public void applyInterest() {
        int yearsFromCreation = Period.between(getCreationDate(),LocalDate.now()).getYears();
        if (yearsFromCreation >= 1 && getInterestDate() == getCreationDate().plusYears(1)) {
            setBalance(super.getBalance().add(super.getBalance().multiply(getInterestRate().multiply(BigDecimal.valueOf(yearsFromCreation)))));
            setInterestDate(LocalDate.now());
        } else if (Period.between(interestDate, LocalDate.now()).getYears() >=1 ) {
            int yearsFromInterest = Period.between(interestDate, LocalDate.now()).getYears();
            setBalance(super.getBalance().add(super.getBalance().multiply(getInterestRate().multiply(BigDecimal.valueOf(yearsFromInterest)))));
            setInterestDate(LocalDate.now());
        }
    }

    @Override
    public BigDecimal getBalance() {
        applyInterest();
        return super.getBalance();
    }
}
