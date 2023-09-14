package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientLoanService clientLoanService;


    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getAllLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> loanRequest(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                              Authentication authentication) {
        if(authentication==null){
            return new ResponseEntity<>("Logaut", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getDestinationAccount()==null){
            return new ResponseEntity<>("Missing account", HttpStatus.FORBIDDEN);

        }
        if(loanApplicationDTO.getPayments()==null){
            return new ResponseEntity<>("Missing paymets", HttpStatus.FORBIDDEN);

        }
        if(loanApplicationDTO.getAmount()==null){
            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getLoanTypeId()==null){
            return new ResponseEntity<>("Missing loan type", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.getLoanById(loanApplicationDTO.getLoanTypeId());
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.getAcctounByNumber(loanApplicationDTO.getDestinationAccount());

        if (loan == null) {
            return new ResponseEntity<>("Incorrect loan type", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("Account used to apply to the loan do not exist", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0|| loanApplicationDTO.getAmount().isNaN() ||loanApplicationDTO.getAmount().isInfinite() ) {
            return new ResponseEntity<>("Wrong amount", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("Incorrect payments amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("That number of payments is not allowed", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("Exceeded the loan maximun amount", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().stream().noneMatch(chocolate -> chocolate.getNumber().equals(loanApplicationDTO.getDestinationAccount()))) {
            return new ResponseEntity<>("7-Account do not belongs to the client", HttpStatus.FORBIDDEN);
        }
        double plusPercentage = (loanApplicationDTO.getAmount() * loan.getPercentage()/100) + (loanApplicationDTO.getAmount());
        ClientLoan newLoan = new ClientLoan(plusPercentage, loanApplicationDTO.getPayments(),client,loan);
        Transaction newTransaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), "loan: "+loan.getName() , LocalDateTime.now(), account);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        account.addTransaction(newTransaction);
        client.addClientLoan(newLoan);
        loan.addClientLoan(newLoan);
        transactionService.saveTransaction(newTransaction);
        clientLoanService.saveClientLoan(newLoan);
        accountService.saveAccount(account);
        loanService.saveLoan(loan);
        clientService.saveClient(client);
        return new ResponseEntity<>("Loan added to the account", HttpStatus.CREATED);

    }

}
