package com.ironhack.BankingSystem.controllers.interfaces;

import com.ironhack.BankingSystem.models.Transaction;
import com.ironhack.BankingSystem.models.accounts.Account;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountHolderControllerInt {
List<Account> listMyPrimaryAccounts(Long userId);

List<Account> listMySecondaryAccounts(Long userId);

BigDecimal getBalanceByAccountId (Long userId, Long accountId);

Transaction transferByAccountId(BigDecimal amount, Long originAccountId, Long targetAccountId, Optional<String> primaryOwner, Optional<String> secondaryOwner);

}
