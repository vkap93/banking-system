package com.ironhack.BankingSystem.controllers;

import com.ironhack.BankingSystem.controllers.interfaces.AdminControllerInt;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.accounts.CreditCard;
import com.ironhack.BankingSystem.models.accounts.Savings;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.users.ThirdParty;
import com.ironhack.BankingSystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController implements AdminControllerInt {

    @Autowired
    AdminService adminService;

    @PostMapping("/create-account_holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder) {
        return adminService.createAccountHolder(accountHolder);
    }

    @PostMapping("/create-third_party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty) {
        return adminService.createThirdParty(thirdParty);
    }

    @PostMapping("/create-checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestParam Long primaryOwnerId, @RequestParam Optional<Long> secondaryOwnerId) {
        return adminService.createCheckingAccount(primaryOwnerId,secondaryOwnerId);
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
}
