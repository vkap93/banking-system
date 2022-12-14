package com.ironhack.BankingSystem;

import com.ironhack.BankingSystem.models.users.*;
import com.ironhack.BankingSystem.repositories.users.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;

import java.time.LocalDate;

@SpringBootApplication
public class BankingSystemApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User testAdmin = userRepository.save(new Admin("TestAdmin", passwordEncoder.encode("abcd")));
		roleRepository.save(new Role("ADMIN",testAdmin));
		User testAccountHolder = userRepository.save(new AccountHolder("TestUser",passwordEncoder.encode("1234"), LocalDate.now(),null));
		roleRepository.save(new Role("USER", testAccountHolder));
	}
}
