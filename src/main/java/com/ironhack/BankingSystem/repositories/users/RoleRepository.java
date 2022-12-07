package com.ironhack.BankingSystem.repositories.users;

import com.ironhack.BankingSystem.models.users.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

