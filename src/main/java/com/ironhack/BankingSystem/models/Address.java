package com.ironhack.BankingSystem.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {
    private String streetAddress;
    private Long zipCode;
    private String country;
}
