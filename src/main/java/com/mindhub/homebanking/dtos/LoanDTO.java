package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
    private long id;
    private String name;
    private long maxAmount;
    private Integer percentage;
    private List<Integer> payments = new ArrayList<>();

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.percentage= loan.getPercentage();
        this.payments = loan.getPayments();

    }

    public long getId() { return id;}
    public String getName() {
        return name;
    }
    public long getMaxAmount() {
        return maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }

    public Integer getPercentage() {
        return percentage;
    }
}
