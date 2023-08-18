package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanDTO {
    private long id;
    private String name;


    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.name = loan.getName();


    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }




}