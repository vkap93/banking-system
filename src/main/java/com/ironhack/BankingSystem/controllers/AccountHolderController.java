package com.ironhack.BankingSystem.controllers;

import com.ironhack.BankingSystem.controllers.interfaces.AccountHolderControllerInt;
import com.ironhack.BankingSystem.dtos.TransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.services.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountHolderController implements AccountHolderControllerInt {

    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/primary_accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> listMyPrimaryAccounts(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        //check security - authentication principal
        return accountHolderService.listMyPrimaryAccounts(id);
    }

    @GetMapping("/secondary_accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> listMySecondaryAccounts(@PathVariable Long id) {
        //check security - authentication principal
        return accountHolderService.listMySecondaryAccounts(id);
    }

    @GetMapping("/check-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getBalanceByAccountId(@PathVariable("id") Long userId, @RequestParam Long accountId) {
        return accountHolderService.getBalanceByAccountId(userId,accountId);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return accountHolderService.createTransaction(transactionDTO);
    }
}
