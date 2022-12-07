package com.ironhack.BankingSystem.controllers.interfaces;

import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountHolderControllerInt {
List<Account> listMyAccounts();

BigDecimal getBalanceByAccountId (Long accountId);

Transaction transferByAccountId(BigDecimal amount, Long originAccountId, Long targetAccountId, Optional<String> primaryOwner, Optional<String> secondaryOwner);

}
