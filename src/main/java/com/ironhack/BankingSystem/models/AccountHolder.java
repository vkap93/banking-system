package com.ironhack.BankingSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountHolder extends User{

 private String password;
 private LocalDate dateOfBirth;

 @Embedded
 private Address primaryAddress;

 @Embedded
 private Address mailingAddress;

 @JsonIgnore
 @OneToMany (mappedBy = "primaryOwner")
 private List<Account> primaryAccountList;

 @JsonIgnore
 @OneToMany (mappedBy = "secondaryOwner")
 private List<Account> secondaryAccountList;

 public AccountHolder() {
 }

 public AccountHolder(String username, String password, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
  super(username);
  this.password = password;
  this.dateOfBirth = dateOfBirth;
  this.primaryAddress = primaryAddress;
  this.mailingAddress = mailingAddress;
 }

 public AccountHolder(String username, String password, LocalDate dateOfBirth, Address primaryAddress) {
  super(username);
  this.password = password;
  this.dateOfBirth = dateOfBirth;
  this.primaryAddress = primaryAddress;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
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
