package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

public interface TransactionService {

    void saveTransaction(Transaction transaction);
    boolean controlNumber(Double amount);
    boolean controlWrongNumber(Double amount);
    boolean controlString(String text);
}
