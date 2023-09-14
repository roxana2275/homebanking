package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private double balance;
    private boolean active;
    private Account account;


    public TransactionDTO(Transaction transaction) {
        this.id= transaction.getId();
        this.date=transaction.getDate();
        this.type=transaction.getType();
        this.description= transaction.getDescription();
        this.amount= transaction.getAmount();
        this.balance= transaction.getBalance();
    }
    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Account getAccount() {
        return account;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}

