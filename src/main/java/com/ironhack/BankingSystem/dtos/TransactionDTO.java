package com.ironhack.BankingSystem.dtos;

import java.math.BigDecimal;

public class TransactionDTO {

    private Long accountOriginId;

    private Long accountTargetId;

    private BigDecimal amount;

    private Long primaryOwnerId;

    private Long secondaryOwnerId;

    public TransactionDTO() {
    }

    public TransactionDTO(Long accountOriginId, Long accountTargetId, BigDecimal amount, Long primaryOwnerId, Long secondaryOwnerId) {
        this.accountOriginId = accountOriginId;
        this.accountTargetId = accountTargetId;
        this.amount = amount;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
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

    public Long getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Long primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }
}

