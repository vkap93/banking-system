package com.ironhack.BankingSystem.controllers.interfaces;

import com.ironhack.BankingSystem.dtos.TransferDTO;
import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderControllerInt {
List<Account> listMyPrimaryAccounts(UserDetails userDetails);

List<Account> listMySecondaryAccounts(UserDetails userDetails);

BigDecimal getBalanceByAccountId (UserDetails userDetails, Long accountId);

Transaction transfer(@AuthenticationPrincipal UserDetails userDetails, TransferDTO transferDTO);

}
