package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.models.accounts.Checking;
import com.ironhack.BankingSystem.models.accounts.CreditCard;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.repositories.accounts.*;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;
import com.ironhack.BankingSystem.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    public AccountHolder createAccountHolder(AccountHolder accountHolder) {
        return accountHolderRepository.save(accountHolder);
    }

    public Checking createCheckingAccount(Long primaryOwnerId, Optional<Long> secondaryOwnerId) {
        if (secondaryOwnerId.isPresent()) {
                AccountHolder primaryOwner = accountHolderRepository.findById(primaryOwnerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
                AccountHolder secondaryOwner = accountHolderRepository.findById(secondaryOwnerId.get()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for secondary owner not found in database"));
                Checking checking = new Checking(primaryOwner,secondaryOwner);
                primaryOwner.getPrimaryAccountList().add(checking);
                secondaryOwner.getSecondaryAccountList().add(checking);
                accountHolderRepository.save(primaryOwner);
                accountHolderRepository.save(secondaryOwner);
                return checkingRepository.save(checking);
        }
            AccountHolder primaryOwner = accountHolderRepository.findById(primaryOwnerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
            Checking checking = new Checking(primaryOwner,null);
            primaryOwner.getPrimaryAccountList().add(checking);
            accountHolderRepository.save(primaryOwner);
            return checkingRepository.save(checking);
    }
}
