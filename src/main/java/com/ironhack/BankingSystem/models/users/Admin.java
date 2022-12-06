package com.ironhack.BankingSystem.models.users;

import jakarta.persistence.*;

@Entity
public class Admin extends User {
private String password;

    public Admin(String username, String password) {
        super(username);
        this.password = password;
    }

    public Admin() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
