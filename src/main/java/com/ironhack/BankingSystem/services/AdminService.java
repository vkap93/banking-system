package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.models.accounts.*;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.users.Role;
import com.ironhack.BankingSystem.models.users.ThirdParty;
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
        System.out.println(accountHolder.getPassword());
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        AccountHolder newAccountHolder = accountHolderRepository.save(accountHolder);
        roleRepository.save(new Role("USER", accountHolder));
        return newAccountHolder;
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
                return studentCheckingRepository.save(studentChecking);
            } else {
                Checking checking = new Checking(primaryOwner, secondaryOwner);
                return checkingRepository.save(checking);
            }
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
        AccountHolder primaryOwner = accountHolderRepository.findById(savings.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
        if (savings.getSecondaryOwner() != null) {
            accountHolderRepository.findById(savings.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for secondary owner not found in database"));
        }
        Savings newSavings = new Savings();
        newSavings.setPrimaryOwner(primaryOwner);
        newSavings.setSecondaryOwner(savings.getSecondaryOwner());
        newSavings.setInterestRate(savings.getInterestRate());
        newSavings.setMinimumBalance(savings.getMinimumBalance());
        return savingsRepository.save(newSavings);
    }

    public CreditCard createCreditCard(CreditCard creditCard) {
        AccountHolder primaryOwner = accountHolderRepository.findById(creditCard.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for primary owner not found in database"));
        if (creditCard.getSecondaryOwner() != null) {
            accountHolderRepository.findById(creditCard.getPrimaryOwner().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID for secondary owner not found in database"));
        }
        CreditCard newCreditCard = new CreditCard();
        newCreditCard.setPrimaryOwner(primaryOwner);
        newCreditCard.setSecondaryOwner(creditCard.getSecondaryOwner());
        newCreditCard.setInterestRate(creditCard.getInterestRate());
        newCreditCard.setCreditLimit(creditCard.getCreditLimit());
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

