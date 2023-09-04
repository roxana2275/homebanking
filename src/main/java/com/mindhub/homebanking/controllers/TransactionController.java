package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
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
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transactionMaker(@RequestParam String fromAccountNumber,
                                                   @RequestParam String toAccountNumber,
                                                   @RequestParam Double amount,
                                                   @RequestParam String description,
                                                   Authentication authentication){
        if(authentication==null){
            return new ResponseEntity<>("Logaut", HttpStatus.FORBIDDEN);
        }
        Client client =clientService.getClient(authentication);


        if (description.trim().isEmpty()) {
            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
        }
        if (!transactionService.controlNumber(amount)) {
            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);
        }
        if (!transactionService.controlWrongNumber(amount)) {
            return new ResponseEntity<>("Wrong value", HttpStatus.FORBIDDEN);
        }
        if (!transactionService.controlString(fromAccountNumber)) {
            return new ResponseEntity<>("Missing origin account", HttpStatus.FORBIDDEN);
        }
        if (!transactionService.controlString(toAccountNumber)) {
            return new ResponseEntity<>("Missing destination account", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.equals(fromAccountNumber)) {
            return new ResponseEntity<>("Destination account must be different", HttpStatus.FORBIDDEN);
        }
        Account originAccount = accountService.getAcctounByNumber(fromAccountNumber);
        Account destinyAccount = accountService.getAcctounByNumber(toAccountNumber);
        if (!accountService.controlAccount(originAccount)) {
            return new ResponseEntity<>("Origin account do not exist", HttpStatus.FORBIDDEN);
        }
        if (!accountService.controlAccount(destinyAccount)) {
            return new ResponseEntity<>("Destination account do not exist", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount) {
            return new ResponseEntity<>("You don't have enough amount", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(authentication.getName()).getAccounts().stream().noneMatch(account -> account.getNumber().equals(fromAccountNumber))) {
            return new ResponseEntity<>("You cannot transfer to the same account", HttpStatus.FORBIDDEN);
        }
        Transaction debit = new Transaction( DEBIT, (amount*-1), "To " + toAccountNumber + ": " + description, LocalDateTime.now(),originAccount);
        transactionService.saveTransaction(debit);
        Transaction credit = new Transaction(CREDIT,amount, "From " + fromAccountNumber + ": " + description,  LocalDateTime.now(),destinyAccount);
        transactionService.saveTransaction(credit);
        originAccount.addTransaction(debit);
        destinyAccount.addTransaction(credit);
        originAccount.setBalance(originAccount.getBalance() - amount);
        destinyAccount.setBalance(destinyAccount.getBalance() + amount);
        accountService.saveAccount(originAccount);
        accountService.saveAccount(destinyAccount);
        return new ResponseEntity<>("Transaction ok", HttpStatus.OK);
    }
}
