package com.ironhack.BankingSystem.controllers;

import com.ironhack.BankingSystem.controllers.interfaces.AccountHolderControllerInt;
import com.ironhack.BankingSystem.dtos.TransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.services.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountHolderController implements AccountHolderControllerInt {

    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/primary_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> listMyPrimaryAccounts( @AuthenticationPrincipal UserDetails userDetails) {
        return accountHolderService.listMyPrimaryAccounts(userDetails);
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
    public Transaction transfer(@RequestBody TransactionDTO transactionDTO) {
        return accountHolderService.transfer(transactionDTO);
    }
}
