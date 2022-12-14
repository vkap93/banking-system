package com.ironhack.BankingSystem.controllers.interfaces;

import com.ironhack.BankingSystem.models.accounts.*;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.ThirdParty;

import java.math.BigDecimal;
import java.util.Optional;

public interface AdminControllerInt {
  Account createCheckingAccount(Long primaryOwnerId, Optional<Long> secondaryOwnerId);

  Savings createSavingsAccount(Savings savings);

  CreditCard createCreditCard(CreditCard creditCard);

  BigDecimal getAccountBalance(Long id);

  Account modifyAccountBalance(Long id, BigDecimal balance);

  AccountHolder createAccountHolder(AccountHolder accountHolder);

  ThirdParty createThirdParty(ThirdParty thirdParty);

  void deleteAccount(Long id);

}
