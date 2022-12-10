package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.dtos.TransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.repositories.accounts.AccountRepository;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;
    public List<Account> listMyPrimaryAccounts(Long id) {
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID not found in database"));
        return accountRepository.findByPrimaryOwnerId(id);
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
