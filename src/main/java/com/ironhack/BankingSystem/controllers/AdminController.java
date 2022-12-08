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

@RestController
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

    @PostMapping("/create-savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createSavingsAccount(@RequestBody Savings savings) {
        return null;
    }
    @PostMapping("/create-credit_card")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCard(CreditCard creditCard) {
        return null;
    }

    @GetMapping("/check-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getAccountBalance(@PathVariable Long accountId) {
        return null;
    }

    @PatchMapping("/modify-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account modifyAccountBalance(@PathVariable Long accountId, @RequestParam BigDecimal balance) {
        return adminService.modifyAccountBalance(accountId, balance);
    }
}
