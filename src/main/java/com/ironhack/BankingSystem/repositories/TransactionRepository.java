package com.ironhack.BankingSystem.repositories;

import com.ironhack.BankingSystem.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction, Long> {
}
