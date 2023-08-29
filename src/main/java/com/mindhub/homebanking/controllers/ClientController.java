package com.mindhub.homebanking.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @RequestMapping("clients/{current}")
    public ClientDTO getClient(Authentication authentication){

        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

    }
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            if(firstName.isEmpty()){
                return new ResponseEntity<>("Missing First Name", HttpStatus.FORBIDDEN);
            } else if(lastName.isEmpty()){
                return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
            } else if(email.isEmpty()){
                return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
            }
        }

        // Validación de mail
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return new ResponseEntity<>("Invalid email", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client();
        client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        String numberAccount;
        Boolean auxAccount=true;
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

        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrent (Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }


}
