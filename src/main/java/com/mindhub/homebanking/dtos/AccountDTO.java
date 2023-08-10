package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AccountDTO {

    private Long id;
    private String number;

    private LocalDate date;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO() {
    }


        public AccountDTO(Account account) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.date = account.getDate();
            this.balance = account.getBalance();

        this.transactions= new HashSet<>();
        for (Transaction transaction : account.getTransactions()) {
             this.transactions.add(new TransactionDTO(transaction));
            }

    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
