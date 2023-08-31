package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transactionMaker(@RequestParam String fromAccountNumber,
                                                   @RequestParam String toAccountNumber,
                                                   @RequestParam Double amount,
                                                   @RequestParam String description,
                                                   Authentication authentication){
        Client client =clientRepository.findByEmail(authentication.getName());

        Account originAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinyAccount = accountRepository.findByNumber(toAccountNumber);
        if (description.trim().isEmpty()) {
            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
        }
        if (amount.isNaN()) {
            return new ResponseEntity<>("Missing enter amount", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0 || amount.isInfinite()) {
            return new ResponseEntity<>("Wrong value", HttpStatus.FORBIDDEN);
        }
        if (amount == null) {
            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.trim().isEmpty()) {
            return new ResponseEntity<>("Missing origin account", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.trim().isEmpty()) {
            return new ResponseEntity<>("Missing destination account", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.equals(fromAccountNumber)) {
            return new ResponseEntity<>("Destination account must be different", HttpStatus.FORBIDDEN);
        }

        if (originAccount == null) {
            return new ResponseEntity<>("Origin account do not exist", HttpStatus.FORBIDDEN);
        }
        if (destinyAccount == null) {
            return new ResponseEntity<>("Destination account do not exist", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount) {
            return new ResponseEntity<>("You don't have enough amount", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(authentication.getName()).getAccounts().stream().noneMatch(account -> account.getNumber().equals(fromAccountNumber))) {
            return new ResponseEntity<>("You cannot transfer to the same account", HttpStatus.FORBIDDEN);
        }
        Transaction debit = new Transaction( DEBIT, (amount*-1), "To " + toAccountNumber + ": " + description, LocalDateTime.now(),originAccount);
        transactionRepository.save(debit);
        Transaction credit = new Transaction(CREDIT,amount, "From " + fromAccountNumber + ": " + description,  LocalDateTime.now(),destinyAccount);
        transactionRepository.save(credit);
        originAccount.addTransaction(debit);
        destinyAccount.addTransaction(credit);
        originAccount.setBalance(originAccount.getBalance() - amount);
        destinyAccount.setBalance(destinyAccount.getBalance() + amount);
        accountRepository.save(originAccount);
        accountRepository.save(destinyAccount);
        return new ResponseEntity<>("Transaction ok", HttpStatus.OK);
    }
}
