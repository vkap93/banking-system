package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.enums.AccountStatus;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.Transaction;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer = 13, fraction = 2)
    @Min(0L)
    private BigDecimal balance;
    @ManyToOne
    private AccountHolder primaryOwner;

    @Nullable
    @ManyToOne
    private AccountHolder secondaryOwner;
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "accountOrigin")
    private List<Transaction> transfersOut;

    @OneToMany(mappedBy = "accountTarget")
    private List<Transaction> transfersIn;

    protected Account() {
    }

    public Account(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
        this.accountStatus = AccountStatus.ACTIVE;
        this.creationDate = LocalDate.now();
    }

    public Account(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.accountStatus = AccountStatus.ACTIVE;
        this.creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Transaction> getTransfersOut() {
        return transfersOut;
    }

    public void setTransfersOut(List<Transaction> transfersOut) {
        this.transfersOut = transfersOut;
    }

    public List<Transaction> getTransfersIn() {
        return transfersIn;
    }

    public void setTransfersIn(List<Transaction> transfersIn) {
        this.transfersIn = transfersIn;
    }

    public void updateBalance(BigDecimal balanceChange) {
        if (balanceChange.compareTo(BigDecimal.ZERO) < 0) {
            balanceChange.negate();
            setBalance(getBalance().subtract(balanceChange));
        } else {
            setBalance(getBalance().add(balanceChange));
        }
    }
}







