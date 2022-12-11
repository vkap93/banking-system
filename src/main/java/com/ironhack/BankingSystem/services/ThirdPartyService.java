package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.dtos.ThirdPartyTransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.users.ThirdParty;
import com.ironhack.BankingSystem.repositories.TransactionRepository;
import com.ironhack.BankingSystem.repositories.accounts.AccountRepository;
import com.ironhack.BankingSystem.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;


@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction send(String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        //Find third party with hashed key provided
        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Third Party not found in database"));
        //Retrieve target account to perform a transaction (send amount), if present
        Account accountTarget = accountRepository.findById(thirdPartyTransactionDTO.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account ID not found in database"));
       //Throw CONFLICT status if secret key does not match the target account
        if (!thirdPartyTransactionDTO.getAccountSecretKey().equals(accountTarget.getSecretKey())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Secret Key is not correct");
        }
        //Throw CONFLICT status if amount is not positive
        if (thirdPartyTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Please provide a positive amount to send");
        }
        //Update balance of target account and return newly created transaction
            accountTarget.updateBalance(thirdPartyTransactionDTO.getAmount());
            return transactionRepository.save(new Transaction(thirdPartyTransactionDTO.getAmount(),null, accountTarget, thirdParty.getName()));
    }

    public Transaction receive(String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Third Party not found in database"));
        Account accountOrigin = accountRepository.findById(thirdPartyTransactionDTO.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Origin Account ID not found in database"));
        //Throw CONFLICT status if amount is not positive
        if (thirdPartyTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Please provide a positive amount to receive");
        }
        //Throw CONFLICT status if amount is below origin balance
        if (thirdPartyTransactionDTO.getAmount().compareTo(accountOrigin.getBalance()) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account has not enough balance to cover transaction");
        }
        //Throw CONFLICT status if secret key does not match the target account
        if (!thirdPartyTransactionDTO.getAccountSecretKey().equals(accountOrigin.getSecretKey())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Secret Key is not correct");
        }
        //Update balance of origin account and return newly created transaction
        accountOrigin.updateBalance(thirdPartyTransactionDTO.getAmount().negate());
        return transactionRepository.save(new Transaction(thirdPartyTransactionDTO.getAmount(),accountOrigin, null, thirdParty.getName()));
    }
}
