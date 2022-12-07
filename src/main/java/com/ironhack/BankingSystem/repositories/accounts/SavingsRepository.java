package com.ironhack.BankingSystem.repositories.accounts;

import com.ironhack.BankingSystem.models.accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository <Savings, Long> {
}