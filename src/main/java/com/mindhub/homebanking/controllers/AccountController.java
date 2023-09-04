package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

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
        return accountService.getAccounts(authentication);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client =clientService.getClient(authentication);
        String numberAccount;
        Boolean auxAccount=true;
        if(client.getAccounts().size()>3){
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

}
