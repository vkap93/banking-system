package com.ironhack.BankingSystem.repositories.users;

import com.ironhack.BankingSystem.models.accounts.Account;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder,Long> {
     Optional <Account> findPrimaryAccountById (Long accountId);

     Optional <AccountHolder> findByUsername (String username);
}
