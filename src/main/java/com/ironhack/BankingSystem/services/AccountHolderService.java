package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.dtos.TransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.users.User;
import com.ironhack.BankingSystem.repositories.accounts.AccountRepository;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;
import com.ironhack.BankingSystem.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;
    public List<Account> listMyPrimaryAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User accountHolder = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not registered as an account holder"));
        return accountRepository.findByPrimaryOwnerId(accountHolder.getId());
    }

    public List<Account> listMySecondaryAccounts(Long id) {
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found in database"));
        return accountRepository.findBySecondaryOwnerId(id);
    }

    public BigDecimal getBalanceByAccountId(Long userId, Long accountId) {
        AccountHolder accountHolder = accountHolderRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found in database"));
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account ID not found in database"));
        for (Account p : accountHolder.getPrimaryAccountList()) {
            if (p.getId().equals(accountId)) {
                return p.getBalance();
            }
        }
        for  (Account s : accountHolder.getSecondaryAccountList()) {
            if (s.getId().equals(accountId)) {
                return s.getBalance();
            }
        }
        return null;
    }

    public Transaction transfer(TransactionDTO transactionDTO){
        return null;
    }


}
