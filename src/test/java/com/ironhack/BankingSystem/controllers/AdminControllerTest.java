package com.ironhack.BankingSystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.BankingSystem.models.Address;
import com.ironhack.BankingSystem.models.ThirdParty;
import com.ironhack.BankingSystem.models.users.*;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @BeforeEach
    void setUp() {
        //Building mock by setting up the App context
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldCreateNewAccountHolder_whenPostIsRequested() throws Exception {
        AccountHolder accountHolder = new AccountHolder("John Johnson", "1234", LocalDate.of(1990,1,15), new Address("Baker Street 23", "ZP578", "London", "UK"));
        //Converting accountHolder to JSON
        String body = objectMapper.writeValueAsString(accountHolder);
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
}