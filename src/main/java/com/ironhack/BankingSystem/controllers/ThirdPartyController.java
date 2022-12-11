package com.ironhack.BankingSystem.controllers;

import com.ironhack.BankingSystem.controllers.interfaces.ThirdPartyControllerInt;
import com.ironhack.BankingSystem.dtos.ThirdPartyTransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThirdPartyController implements ThirdPartyControllerInt {

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction send(@RequestHeader String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        return null;
    }
    @PostMapping("/receive")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction receive(@RequestHeader String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        return null;
    }


}
