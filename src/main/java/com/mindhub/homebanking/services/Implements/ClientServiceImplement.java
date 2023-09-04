package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Override
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    @Override
    public Client getClient(Authentication authentication){
        Client client =clientRepository.findByEmail(authentication.getName());
        return client;
    }
    public boolean controlString(String text){
        boolean result=true;
        if(text.isEmpty()){
            result=false;
        }
        return result;
    }
    public Matcher controlEmail(String email){
        boolean result=true;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher;
    }
    @Override
    public ClientDTO getCurrent (Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
