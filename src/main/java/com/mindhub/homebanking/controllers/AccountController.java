package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccount() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){

        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);

    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client =clientRepository.findByEmail(authentication.getName());
        String numberAccount;
        Boolean auxAccount=true;
        if(client.getAccounts().size()>3){
            return new ResponseEntity<>("You cannot have more than 3 accounts", HttpStatus.FORBIDDEN);
        }else{
            do{
                Random random = new Random();
                int numeroAleatorio = random.nextInt(100000000);
                numberAccount = "VIN" + numeroAleatorio;
                Account account = accountRepository.findByNumber(numberAccount);
                if(account==null){
                    auxAccount=false;
                }
            }while (auxAccount);
            Account account = new Account(numberAccount, LocalDate.now(),0);
            account.setClient(client);
            accountRepository.save(account);
            client.addAccount(account);
            clientRepository.save(client);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

}
