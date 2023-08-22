package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private Long loanId;
    private String name;
    private double Amount;
    private int Payments;
    private  Client  client;


    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId=clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.Amount = clientLoan.getAmount();
        this.Payments = clientLoan.getPayments();
        this.client = clientLoan.getClient();
    }

    public long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return Amount;
    }

    public int getPayments() {
        return Payments;
    }

    public Client getClient() {
        return client;
    }
}
