package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

    @GetMapping("clients/{current}")
    public ClientDTO getClient(Authentication authentication){

        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

            if(!clientService.controlString(firstName)){
                return new ResponseEntity<>("Missing First Name", HttpStatus.FORBIDDEN);
            }
            if(!clientService.controlString(lastName)){
                return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
            }
            if(!clientService.controlString(email)){
                return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
            }
            if(!clientService.controlString(password)){
                return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
            }

        if (!clientService.controlEmail(email).matches()) {
            return new ResponseEntity<>("Invalid email", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        String numberAccount;
        Boolean auxAccount=true;
        do{
            Random random = new Random();
            int numeroAleatorio = random.nextInt(100000000);
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

        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrent (Authentication authentication) {
        return clientService.getCurrent(authentication);
    }


}
