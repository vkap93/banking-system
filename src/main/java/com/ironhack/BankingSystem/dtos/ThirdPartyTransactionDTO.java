package com.ironhack.BankingSystem.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ThirdPartyTransactionDTO {

    @NotNull
    private Long accountId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String accountSecretKey;

    public ThirdPartyTransactionDTO() {
    }

    public ThirdPartyTransactionDTO(Long accountId, BigDecimal amount, String accountSecretKey) {
        this.accountId = accountId;
        this.amount = amount;
        this.accountSecretKey = accountSecretKey;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountSecretKey() {
        return accountSecretKey;
    }

    public void setAccountSecretKey(String accountSecretKey) {
        this.accountSecretKey = accountSecretKey;
    }
}
