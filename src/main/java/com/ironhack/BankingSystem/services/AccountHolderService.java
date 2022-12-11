package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.dtos.TransferDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.repositories.TransactionRepository;
import com.ironhack.BankingSystem.repositories.accounts.AccountRepository;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;
    public List<Account> listMyPrimaryAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        //In this case, isPresent() or orElseThrow() should not be necessary since access is restricted to USER (Account Holder)
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).get();
        //Return list of all primary accounts
        return accountRepository.findByPrimaryOwnerId(accountHolder.getId());
    }

    public List<Account> listMySecondaryAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        //In this case, isPresent() or orElseThrow() should not be necessary since access is restricted to USER (Account Holder)
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).get();
        //Return list of all primary accounts
        return accountRepository.findBySecondaryOwnerId(accountHolder.getId());
    }

    public BigDecimal getBalanceByAccountId(@AuthenticationPrincipal UserDetails userDetails, Long accountId) {
        //Find account holder with username of logged-in user
        String username = userDetails.getUsername();
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).get();
        //Retrieve account, if present. Return NOT FOUND otherwise
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account ID not found in database"));
        //Return account balance, if it is a primary or secondary account of this account holder. Return NOT FOUND otherwise
        if (accountHolder.getPrimaryAccountList().contains(account) || accountHolder.getSecondaryAccountList().contains(account)) {
            return account.getBalance();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has no account with the given Account ID");
        }
    }

    public Transaction transfer(@AuthenticationPrincipal UserDetails userDetails, TransferDTO transferDTO){
        //Find account holder with username of logged-in user
        String username = userDetails.getUsername();
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).get();
        //Retrieve origin and target accounts to create a transaction (perform transfer), if present
        Account accountOrigin = accountRepository.findById(transferDTO.getAccountOriginId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Origin Account ID not found in database"));
        Account accountTarget = accountRepository.findById(transferDTO.getAccountTargetId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target Account ID not found in database"));
        if (transferDTO.getAccountTargetOwnerId() == null || (transferDTO.getAccountTargetOwnerId() != accountTarget.getPrimaryOwner().getId() && transferDTO.getAccountTargetOwnerId() != accountTarget.getSecondaryOwner().getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Please provide a valid primary or secondary owner for the target account");
        }
        //Check if transfer amount is negative or if below origin balance, if yes return exception
        if (transferDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0 || transferDTO.getAmount().compareTo(accountOrigin.getBalance()) > 0)  {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Please provide a positive amount to transfer that is below the account balance");
        }
        //Check if origin account is not in primary nor in secondary account list of account holder
        if (!accountHolder.getPrimaryAccountList().contains(accountOrigin) || !accountHolder.getSecondaryAccountList().contains(accountOrigin)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has no account with the given Account ID");
        }
        //Update balances of each account
        accountOrigin.updateBalance(transferDTO.getAmount().negate());
        accountTarget.updateBalance(transferDTO.getAmount());
        //Return newly created transaction
        return transactionRepository.save (new Transaction(transferDTO.getAmount(),accountOrigin, accountTarget, null));
    }
}
