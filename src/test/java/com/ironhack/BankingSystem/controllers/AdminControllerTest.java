package com.ironhack.BankingSystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.BankingSystem.models.Address;
import com.ironhack.BankingSystem.models.ThirdParty;
import com.ironhack.BankingSystem.models.users.*;
import com.ironhack.BankingSystem.repositories.accounts.AccountRepository;
import com.ironhack.BankingSystem.repositories.accounts.CheckingRepository;
import com.ironhack.BankingSystem.repositories.accounts.StudentCheckingRepository;
import com.ironhack.BankingSystem.repositories.users.AccountHolderRepository;
import com.ironhack.BankingSystem.repositories.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private WebApplicationContext context;

    //MockMvc: to mock Http requests
    MockMvc mockMvc;

    //ObjectMapper: to convert objects to JSON format
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    UserRepository userRepository;

    AccountHolder accountHolder;

    AccountHolder accountHolder2;
    @Autowired
    private CheckingRepository checkingRepository;

    @BeforeEach
    void setUp() {
        //Building mock by setting up the App context
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper.registerModule(new JavaTimeModule());
        accountHolder = accountHolderRepository.save(new AccountHolder("John", "1234", LocalDate.of(1990,1,15), new Address("Baker Street 23", "ZP578", "London", "UK")));
        accountHolder2 =accountHolderRepository.save(new AccountHolder("Marry", "1234", LocalDate.of(2000,2,10), new Address("Test Street 2", "TT578", "London", "UK")));
    }

    @Test
    void shouldCreateNewAccountHolder_whenPostIsRequested() throws Exception {
        AccountHolder user = new AccountHolder("John Johnson", "1234", LocalDate.of(1990,1,15), new Address("Baker Street 23", "ZP578", "London", "UK"));
       //Converting accountHolder to JSON
        String body = objectMapper.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/admin/create-account_holder").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("John"));
    }

    @Test
    void shouldCreateThirdParty_whenPostIsRequested() throws Exception {
        ThirdParty thirdParty = new ThirdParty("Test ThirdParty", "543");
        //Converting thirdParty to JSON
        String body = objectMapper.writeValueAsString(thirdParty);
        MvcResult result = mockMvc.perform(post("/admin/create-third_party").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Test"));
    }

    @Test
    void shouldCreateChecking_whenPostIsRequested() throws Exception {
        Long primaryId = accountHolder.getId();
        Long secondaryId = accountHolder2.getId();
        MvcResult result = mockMvc.perform(post("/admin/create-checking")
                .param("primaryOwnerId", primaryId.toString()).param("secondaryOwnerId",secondaryId.toString())).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(accountHolderRepository.findById(primaryId).get().getUsername()));
        assertTrue(result.getResponse().getContentAsString().contains(accountHolderRepository.findById(secondaryId).get().getPrimaryAddress().getAddress()));
    }

    @Test
    void shouldCreateStudentChecking_whenAgeLessThan24() throws Exception {
        Long primaryId = accountHolder2.getId();
        MvcResult result = mockMvc.perform(post("/admin/create-checking")
                .param("primaryOwnerId", primaryId.toString())).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(studentCheckingRepository.findById(2L).get().getPrimaryOwner().getUsername()));
    }

    @Test
    void shouldReturnError_whenPrimaryOrSecondaryOwnerIdIsNotFound() throws Exception {
        MvcResult result = mockMvc.perform(post("/admin/create-checking")
                .param("primaryOwnerId", "100")).andExpect(status().isNotFound()).andReturn();
        assertEquals("ID for primary owner not found in database", result.getResponse().getErrorMessage());
        MvcResult result2 = mockMvc.perform(post("/admin/create-checking")
                .param("primaryOwnerId", accountHolder.getId().toString()).param("secondaryOwnerId","100")).andExpect(status().isNotFound()).andReturn();
        assertEquals("ID for secondary owner not found in database", result2.getResponse().getErrorMessage());
    }
}