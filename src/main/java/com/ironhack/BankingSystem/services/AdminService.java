package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.models.accounts.*;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.users.Role;
import com.ironhack.BankingSystem.models.ThirdParty;
import com.ironhack.BankingSystem.repositories.accounts.*;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;
import com.ironhack.BankingSystem.repositories.users.RoleRepository;
import com.ironhack.BankingSystem.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.math.BigDecimal;
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

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AccountHolder createAccountHolder(AccountHolder accountHolder) {
        //Retrieve password for account holder and convert it to encoded format
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        //Save and return new account holder and assign its role to user
        AccountHolder newAccountHolder = accountHolderRepository.save(accountHolder);
        roleRepository.save(new Role("USER", accountHolder));
        return newAccountHolder;
    }

    public ThirdParty createThirdParty(ThirdParty thirdParty) {
        //Save and return new third party
        return thirdPartyRepository.save(thirdParty);
    }

    public Account createCheckingAccount(Long primaryOwnerId, Optional<Long> secondaryOwnerId) {
        //Check if given primary owner exists, otherwise throw NOT FOUND
        AccountHolder primaryOwner = accountHolderRepository.findById(primaryOwnerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
        //If secondary owner is present, check if it exists, then create either a student checking or checking account (depending on age of primary owner)
        if (secondaryOwnerId.isPresent()) {
            AccountHolder secondaryOwner = accountHolderRepository.findById(secondaryOwnerId.get()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for secondary owner not found in database"));
            if (Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears() < 24) {
                StudentChecking studentChecking = new StudentChecking(primaryOwner, secondaryOwner);
                return studentCheckingRepository.save(studentChecking);
            } else {
                Checking checking = new Checking(primaryOwner, secondaryOwner);
                return checkingRepository.save(checking);
            }
        //If secondary owner is not given, then create either a student checking or checking account (only with the given primary owner, considering age)
        } else {
            if (Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears() < 24) {
                StudentChecking studentChecking = new StudentChecking(primaryOwner, null);
                return studentCheckingRepository.save(studentChecking);
            } else {
                Checking checking = new Checking(primaryOwner, null);
                return checkingRepository.save(checking);
            }
        }
    }

    public Savings createSavingsAccount(Savings savings) {
        //Check if given primary owner (from savings) exists, otherwise throw NOT FOUND
        AccountHolder primaryOwner = accountHolderRepository.findById(savings.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
        //In case secondary owner is given (from savings), check if it exists and throw NOT FOUND otherwise
        if (savings.getSecondaryOwner() != null) {
            accountHolderRepository.findById(savings.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for secondary owner not found in database"));
        }
        //Create new Savings object using values parsed from savings
        Savings newSavings = new Savings(primaryOwner,savings.getSecondaryOwner(),savings.getInterestRate(),savings.getMinimumBalance() );
        //Return and save new savings account
        return savingsRepository.save(newSavings);
    }

    public CreditCard createCreditCard(CreditCard creditCard) {
        //Check if given primary owner (from creditCard) exists, otherwise throw NOT FOUND
        AccountHolder primaryOwner = accountHolderRepository.findById(creditCard.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
        //In case secondary owner is given (from creditCard), check if it exists and throw NOT FOUND otherwise
        if (creditCard.getSecondaryOwner() != null) {
            accountHolderRepository.findById(creditCard.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for secondary owner not found in database"));
        }
        //Create new CreditCard object using values parsed from savings
        CreditCard newCreditCard = new CreditCard(primaryOwner, creditCard.getSecondaryOwner(), creditCard.getCreditLimit(),creditCard.getInterestRate());
        //Return and save new credit card account
        return creditCardRepository.save(newCreditCard);
    }

    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account ID not found in database"));
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Account cannot be deleted with a remaining balance. Please transfer balance to another account");
        }
        accountRepository.deleteById(accountId);
    }

    public BigDecimal getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account ID not found in database"));
        return account.getBalance();
    }

    public Account modifyAccountBalance(Long accountId, BigDecimal balance) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account ID not found in database"));
        account.setBalance(balance);
        return accountRepository.save(account);
    }
}

