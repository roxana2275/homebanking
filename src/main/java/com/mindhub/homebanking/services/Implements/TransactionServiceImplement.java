package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    public boolean controlNumber(Double amount){
        boolean result=true;
        if(amount.isNaN()||amount == null){
            result=false;
        }
        return result;
    }
    public boolean controlWrongNumber(Double amount){
        boolean result=true;
        if(amount <= 0 || amount.isInfinite()){
            result=false;
        }
        return result;
    }
    public boolean controlString(String text){
        boolean result=true;
        if(text.isEmpty()){
            result=false;
        }
        return result;
    }
    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
