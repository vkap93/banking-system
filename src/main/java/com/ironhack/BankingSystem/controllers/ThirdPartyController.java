package com.ironhack.BankingSystem.controllers;

import com.ironhack.BankingSystem.controllers.interfaces.ThirdPartyControllerInt;
import com.ironhack.BankingSystem.dtos.ThirdPartyTransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.services.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThirdPartyController implements ThirdPartyControllerInt {

    @Autowired
    ThirdPartyService thirdPartyService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction send(@RequestHeader("hashed-key") String hashedKey, @RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        return thirdPartyService.send(hashedKey, thirdPartyTransactionDTO);
    }
    @PostMapping("/receive")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction receive(@RequestHeader("hashed-key") String hashedKey, @RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        return thirdPartyService.receive(hashedKey, thirdPartyTransactionDTO);
    }

}
