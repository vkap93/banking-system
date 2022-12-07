package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.models.users.User;
import com.ironhack.BankingSystem.repositories.users.UserRepository;
import com.ironhack.BankingSystem.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User does not exist");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());

        return customUserDetails;

    }
}

