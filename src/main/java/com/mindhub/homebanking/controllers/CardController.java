package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication){
        Client client =clientRepository.findByEmail(authentication.getName());
        Set<Card> card = client.getCards();
        String auxCardNumber;
        card = card.stream().filter(card1 -> card1.getType().equals(cardType)).collect(Collectors.toSet());
        if (card.size() >= 3) {
            return new ResponseEntity<>("You cannot have more than three cards", HttpStatus.FORBIDDEN);
        }else{
            do {
                auxCardNumber = (int) ((Math.random() * 10000)) + " " + (int) ((Math.random() * 10000)) + " " + (int) ((Math.random() * 10000)) + " " + (int) ((Math.random() * 10000));
            }while (cardRepository.findByNumber(auxCardNumber) != null);
        }
        int cvv=(int) ((Math.random() * 1000));
        Card newCard = new Card(cardType , cardColor,auxCardNumber, cvv, LocalDate.now(), LocalDate.now().plusYears(5),client);
        cardRepository.save(newCard);
        client.addCard(newCard);
        clientRepository.save(client);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

}
