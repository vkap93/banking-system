package com.ironhack.BankingSystem.models;

import com.ironhack.BankingSystem.models.accounts.Account;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal transactionAmount;

    @ManyToOne
    @JoinColumn(name = "account_origin")
    private Account accountOrigin;

    @ManyToOne
    @JoinColumn(name = "account_target")
    private Account accountTarget;

    private String thirdPartyName;

    private LocalDate creationDate;

    public Transaction(BigDecimal transactionAmount, Account accountOrigin, Account accountTarget, String thirdPartyName) {
        this.transactionAmount = transactionAmount;
        this.accountOrigin = accountOrigin;
        this.accountTarget = accountTarget;
        this.creationDate = LocalDate.now();
        this.thirdPartyName = thirdPartyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransferAmount() {
        return transactionAmount;
    }

    public void setTransferAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Account getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOriginId(Account accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public Account getAccountTargetId() {
        return accountTarget;
    }

    public void setAccountTarget(Account accountTarget) {
        this.accountTarget = accountTarget;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
