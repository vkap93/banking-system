package com.ironhack.BankingSystem.repositories.accounts;

import com.ironhack.BankingSystem.models.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository <StudentChecking, Long> {
}