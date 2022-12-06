package com.ironhack.BankingSystem.models.users;

import jakarta.persistence.*;

@Entity
public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin() {
    }
}
