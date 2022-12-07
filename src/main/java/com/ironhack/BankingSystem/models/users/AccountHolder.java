package com.ironhack.BankingSystem.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.BankingSystem.models.Address;
import com.ironhack.BankingSystem.models.accounts.Account;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountHolder extends User {

 private LocalDate dateOfBirth;

 @Embedded
 private Address primaryAddress;

 @Embedded
 @AttributeOverrides({
         @AttributeOverride(name="address",column=@Column(name="mail_address")),
         @AttributeOverride(name="city",column=@Column(name="mail_city")),
         @AttributeOverride(name="postalCode",column=@Column(name="mail_postal")),
         @AttributeOverride(name="country",column=@Column(name="mail_country")),
 })
 private Address mailingAddress;

 @JsonIgnore
 @OneToMany (mappedBy = "primaryOwner", fetch = FetchType.EAGER)
 private List<Account> primaryAccountList;

 @JsonIgnore
 @OneToMany (mappedBy = "secondaryOwner")
 private List<Account> secondaryAccountList;

 public AccountHolder() {
 }

 public AccountHolder(String username, String password, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
  super(username,password);
  this.dateOfBirth = dateOfBirth;
  this.primaryAddress = primaryAddress;
  this.mailingAddress = mailingAddress;
 }

 public AccountHolder(String username, String password, LocalDate dateOfBirth, Address primaryAddress) {
  super(username,password);
  this.dateOfBirth = dateOfBirth;
  this.primaryAddress = primaryAddress;
 }

 public LocalDate getDateOfBirth() {
  return dateOfBirth;
 }

 public void setDateOfBirth(LocalDate dateOfBirth) {
  this.dateOfBirth = dateOfBirth;
 }

 public Address getPrimaryAddress() {
  return primaryAddress;
 }

 public void setPrimaryAddress(Address primaryAddress) {
  this.primaryAddress = primaryAddress;
 }

 public Address getMailingAddress() {
  return mailingAddress;
 }

 public void setMailingAddress(Address mailingAddress) {
  this.mailingAddress = mailingAddress;
 }

 public List<Account> getPrimaryAccountList() {
  return primaryAccountList;
 }

 public void setPrimaryAccountList(List<Account> primaryAccountList) {
  this.primaryAccountList = primaryAccountList;
 }

 public List<Account> getSecondaryAccountList() {
  return secondaryAccountList;
 }

 public void setSecondaryAccountList(List<Account> secondaryAccountList) {
  this.secondaryAccountList = secondaryAccountList;
 }
}
