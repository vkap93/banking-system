package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.models.Address;
import com.ironhack.BankingSystem.models.users.*;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    AccountHolder accountHolder = new AccountHolder("John Johnson", "1234", LocalDate.of(1990,1,15), new Address("Baker Street 23", "ZP578", "London", "UK"));


    @Test
    void shouldCreateAccountHolder_whenSaved() {
       adminService.createAccountHolder(accountHolder);
       assertEquals(accountHolder.getUsername(), accountHolderRepository.findById(1L).get().getUsername());
    }

    @Test
    void shouldCreateCheckingAccount_whenSaved() {
        adminService.createCheckingAccount(1L, java.util.Optional.empty());
    }
}
