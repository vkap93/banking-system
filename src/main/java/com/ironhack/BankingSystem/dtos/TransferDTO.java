package com.ironhack.BankingSystem.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferDTO {

    @NotNull
    private Long accountOriginId;

    @NotNull
    private Long accountTargetId;

    @NotNull
    private BigDecimal amount;

    private Long accountTargetOwnerId;

    public TransferDTO() {
    }

    public TransferDTO(Long accountOriginId, Long accountTargetId, BigDecimal amount, Long accountTargetOwnerId) {
        this.accountOriginId = accountOriginId;
        this.accountTargetId = accountTargetId;
        this.amount = amount;
        this.accountTargetOwnerId = accountTargetOwnerId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountTargetOwnerId() {
        return accountTargetOwnerId;
    }

    public void setAccountTargetOwnerId(Long accountTargetOwnerId) {
        this.accountTargetOwnerId = accountTargetOwnerId;
    }
}

