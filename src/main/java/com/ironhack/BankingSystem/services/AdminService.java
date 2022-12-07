package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.accounts.Checking;
import com.ironhack.BankingSystem.models.accounts.CreditCard;
import com.ironhack.BankingSystem.models.accounts.StudentChecking;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.users.ThirdParty;
import com.ironhack.BankingSystem.repositories.accounts.*;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;
import com.ironhack.BankingSystem.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
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

    public ThirdParty createThirdParty(ThirdParty thirdParty) {
        return thirdPartyRepository.save(thirdParty);
    }

    public Account createCheckingAccount(Long primaryOwnerId, Optional<Long> secondaryOwnerId) {
        AccountHolder primaryOwner = accountHolderRepository.findById(primaryOwnerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
        if (secondaryOwnerId.isPresent()) {
            AccountHolder secondaryOwner = accountHolderRepository.findById(secondaryOwnerId.get()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for secondary owner not found in database"));
            if (Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears() < 24) {
                StudentChecking studentChecking = new StudentChecking(primaryOwner, secondaryOwner);
                primaryOwner.getPrimaryAccountList().add(studentChecking);
                secondaryOwner.getSecondaryAccountList().add(studentChecking);
                accountHolderRepository.save(primaryOwner);
                accountHolderRepository.save(secondaryOwner);
                return studentCheckingRepository.save(studentChecking);
            } else {
                Checking checking = new Checking(primaryOwner, secondaryOwner);
                primaryOwner.getPrimaryAccountList().add(checking);
                secondaryOwner.getSecondaryAccountList().add(checking);
                accountHolderRepository.save(primaryOwner);
                accountHolderRepository.save(secondaryOwner);
                return checkingRepository.save(checking);
            }
        } else {
            if (Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears() < 24) {
                StudentChecking studentChecking = new StudentChecking(primaryOwner, null);
                primaryOwner.getPrimaryAccountList().add(studentChecking);
                accountHolderRepository.save(primaryOwner);
                return studentCheckingRepository.save(studentChecking);
            } else {
                Checking checking = new Checking(primaryOwner, null);
                primaryOwner.getPrimaryAccountList().add(checking);
                accountHolderRepository.save(primaryOwner);
                return checkingRepository.save(checking);
            }
        }
    }
}

