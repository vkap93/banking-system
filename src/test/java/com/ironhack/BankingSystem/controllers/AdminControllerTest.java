package com.ironhack.BankingSystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.BankingSystem.models.Address;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private WebApplicationContext context;

    //MockMvc: to mock Http requests
    MockMvc mockMvc;

    //ObjectMapper: to convert objects to JSON format
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        //Building mock by setting up the App context
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldCreateNewAccountHolder_whenPostIsRequested() throws Exception {
        AccountHolder accountHolder = new AccountHolder("John Johnson", "1234", LocalDate.of(1990,1,15), new Address("Baker Street 23", "ZP578", "London", "UK"));
        //Converting accountHolder to JSON
        String body = objectMapper.writeValueAsString(accountHolder);
        MvcResult result = mockMvc.perform(post("/admin/create-account_holder").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.out.println(result.getResponse());
        assertTrue(result.getResponse().getContentAsString().contains("John"));
    }
}