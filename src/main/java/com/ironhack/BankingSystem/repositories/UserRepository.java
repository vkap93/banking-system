package com.ironhack.BankingSystem.repositories;

import com.ironhack.BankingSystem.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
