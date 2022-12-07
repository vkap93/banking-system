package com.ironhack.BankingSystem.controllers;

import com.ironhack.BankingSystem.controllers.interfaces.AdminControllerInt;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.accounts.Checking;
import com.ironhack.BankingSystem.models.accounts.CreditCard;
import com.ironhack.BankingSystem.models.accounts.Savings;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.users.ThirdParty;

import java.math.BigDecimal;

public class AdminController implements AdminControllerInt {
    public Checking createCheckingAccount(Checking checking) {
        return null;
    }

    public Savings createSavingsAccount(Savings savings) {
        return null;
    }

    public CreditCard createCreditCard(CreditCard creditCard) {
        return null;
    }

    public BigDecimal getAccountBalance(Long id) {
        return null;
    }

    public Account updateAccountBalance(Long id, BigDecimal balance) {
        return null;
    }

    public AccountHolder createAccountHolder(AccountHolder accountHolder) {
        return null;
    }

    public ThirdParty createThirdParty(ThirdParty thirdParty) {
        return null;
    }
}
