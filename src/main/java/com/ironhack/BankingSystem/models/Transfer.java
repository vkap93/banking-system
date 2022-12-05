package com.ironhack.BankingSystem.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal transferAmount;

    @ManyToOne
    @JoinColumn(name = "account_origin_id")
    private Long accountOriginId;

    private Long accountTargetId;

    private LocalDate creationDate;

    public Transfer(BigDecimal transferAmount, Long accountOriginId, Long accountTargetId) {
        this.transferAmount = transferAmount;
        this.accountOriginId = accountOriginId;
        this.accountTargetId = accountTargetId;
        this.creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getAccountOriginId() {
        return accountOriginId;
    }

    public void setAccountOriginId(Long accountOriginId) {
        this.accountOriginId = accountOriginId;
    }

    public Long getAccountTargetId() {
        return accountTargetId;
    }

    public void setAccountTargetId(Long accountTargetId) {
        this.accountTargetId = accountTargetId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
