package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    List<ClientLoanDTO> getAllClientLoans();
    List<LoanDTO> getAllLoans();
    Loan getLoanById(Long id);
    void saveClientLoan(ClientLoan clientLoan);
    void saveLoan(Loan loan);
    boolean controlString(String text);
    boolean controlPayments(Integer pay);
}
