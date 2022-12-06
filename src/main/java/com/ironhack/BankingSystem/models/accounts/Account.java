package com.ironhack.BankingSystem.models.accounts;

import com.ironhack.BankingSystem.enums.AccountStatus;
import com.ironhack.BankingSystem.models.users.AccountHolder;
import com.ironhack.BankingSystem.models.Transfer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;
    @ManyToOne
    private AccountHolder primaryOwner;
    @ManyToOne
    private AccountHolder secondaryOwner;
    private final BigDecimal penaltyFee = BigDecimal.valueOf(40);
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "accountOrigin")
    private List<Transfer> transfersOut;

    @OneToMany(mappedBy = "accountTarget")
    private List<Transfer> transfersIn;

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

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
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

    public List<Transfer> getTransfersOut() {
        return transfersOut;
    }

    public void setTransfersOut(List<Transfer> transfersOut) {
        this.transfersOut = transfersOut;
    }

    public List<Transfer> getTransfersIn() {
        return transfersIn;
    }

    public void setTransfersIn(List<Transfer> transfersIn) {
        this.transfersIn = transfersIn;
    }
}







