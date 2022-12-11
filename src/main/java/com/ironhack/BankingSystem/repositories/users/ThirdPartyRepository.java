package com.ironhack.BankingSystem.repositories.users;

import com.ironhack.BankingSystem.models.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {

    Optional<ThirdParty> findByHashedKey(String hashedKey);
}
