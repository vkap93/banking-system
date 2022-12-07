package com.ironhack.BankingSystem.repositories.accounts;

import com.ironhack.BankingSystem.models.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository <Account, Long> {
}
