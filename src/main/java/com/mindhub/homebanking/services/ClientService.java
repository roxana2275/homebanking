package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.regex.Matcher;

public interface ClientService {
    List<ClientDTO> getClients();
    Client getClient(Authentication authentication);
    ClientDTO getCurrent (Authentication authentication);
    Matcher controlEmail(String email);
    Client findByEmail(String email);
    void saveClient(Client client);
    boolean controlString(String text);
}
