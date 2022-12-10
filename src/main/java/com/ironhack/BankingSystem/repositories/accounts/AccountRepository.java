package com.ironhack.BankingSystem.repositories.accounts;

import com.ironhack.BankingSystem.models.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository <Account, Long> {
    List<Account> findByPrimaryOwnerId (Long primaryOwnerId);

    List<Account> findBySecondaryOwnerId (Long secondaryOwnerId);
}
