package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.models.users.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


@Entity
public class Checking extends Account{

    private final BigDecimal minimumBalance = BigDecimal.valueOf(250);
    private final BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12);
    private final BigDecimal penaltyFee = BigDecimal.valueOf(40);

    private LocalDate maintenanceDate;

    public Checking() {
    }

    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
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

    public void applyMaintenance() {
        int monthsFromCreation = Period.between(getCreationDate(),LocalDate.now()).getMonths();
        if (monthsFromCreation >= 1 && maintenanceDate == null) {
            setBalance(super.getBalance().subtract((monthlyMaintenanceFee).multiply(BigDecimal.valueOf(monthsFromCreation))));
            setMaintenanceDate(LocalDate.now());
        } else if (Period.between(maintenanceDate, LocalDate.now()).getMonths() >=1 ) {
            int monthsFromMaintenance = Period.between(maintenanceDate, LocalDate.now()).getMonths();
            setBalance(super.getBalance().subtract((monthlyMaintenanceFee).multiply(BigDecimal.valueOf(monthsFromMaintenance))));
            setMaintenanceDate(LocalDate.now());
        }
    }

    @Override
    public BigDecimal getBalance() {
        applyMaintenance();
        return super.getBalance();
    }
}
