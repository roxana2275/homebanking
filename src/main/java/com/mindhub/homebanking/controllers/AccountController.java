package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;

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
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccount(Authentication autentication) {
        return accountService.getAccounts(autentication);
    }
    /*
    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id, Authentication authentication){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }
    */

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getOneAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        Account account = accountService.getAccountById(id);
        if (!accountService.controlAccount(account)){
            return new ResponseEntity<>("account not found", HttpStatus.NOT_FOUND);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("is not your account", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
    }
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());
        List<Account> accounts = client.getAccounts().stream().filter(account -> account.isActive()).collect(Collectors.toList());
        return accounts.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());

    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client =clientService.getClient(authentication);
        String numberAccount;
        Boolean auxAccount=true;
        if(client.getAccounts().stream().filter(account -> account.isActive()).count() >3){
            return new ResponseEntity<>("You cannot have more than 3 accounts", HttpStatus.FORBIDDEN);
        }else{
            do{
                Random random = new Random();
                long numeroAleatorio = random.nextInt(100000000);
                numberAccount = "VIN" + numeroAleatorio;
                Account account = accountService.getAcctounByNumber(numberAccount);
                if(account==null){
                    auxAccount=false;
                }
            }while (auxAccount);
            Account account = new Account(numberAccount, LocalDate.now(),0);
            account.setClient(client);
            accountService.saveAccount(account);
            client.addAccount(account);
            clientService.saveClient(client);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    @PatchMapping ("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam String number) {
        if (authentication.getName() == null) {
            return new ResponseEntity<>("Client not authenticated", HttpStatus.FORBIDDEN);
        }
        if (number.isEmpty()) {
            return new ResponseEntity<>("Account number incorrect", HttpStatus.FORBIDDEN);
        }
        Account account = accountService.getAcctounByNumber(number);
        if(!account.isActive()){
            return new ResponseEntity<>("Account number incorrect", HttpStatus.FORBIDDEN);
        }
        List<Transaction> transactions = account.getTransactions().stream().filter(transaction -> transaction.isActive()).collect(Collectors.toList());
        if (account.getBalance() != 0) {
            return new ResponseEntity<>("Account balance must be 0 to delete", HttpStatus.FORBIDDEN);
        }
        transactions.forEach(transaction -> {transaction.setActive(false); transactionService.saveTransaction(transaction);});
        account.setActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }

}
