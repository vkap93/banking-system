package com.ironhack.BankingSystem.controllers.interfaces;

import com.ironhack.BankingSystem.dtos.ThirdPartyTransactionDTO;
import com.ironhack.BankingSystem.models.Transaction;

public interface ThirdPartyControllerInt {

    Transaction send(String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO);

    Transaction receive(String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO);

}
