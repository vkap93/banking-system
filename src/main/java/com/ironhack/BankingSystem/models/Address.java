package com.ironhack.BankingSystem.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private String address;
    private int postalCode;
    private String city;
    private String country;

}