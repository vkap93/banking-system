package com.ironhack.BankingSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountHolder extends User{

 private String password;
 private LocalDate dateOfBirth;
 private Address primaryAddress;
 private Address mailingAddress;

 @JsonIgnore
 @OneToMany (mappedBy = "primaryOwner")
 private List<Account> primaryAccountList;

 @JsonIgnore
 @OneToMany (mappedBy = "secondaryOwner")
 private List<Account> secondaryAccountList;




}
