package com.ironhack.BankingSystem.security;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;

@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {
    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/admin**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/admin**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/admin**").hasRole("ADMIN")

                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}