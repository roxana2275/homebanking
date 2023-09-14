package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Set<Card> card = client.getCards();
        
        
        card = card.stream().filter(card1 -> card1.getType().equals(cardType)).collect(Collectors.toSet());
        Set<Card> colorcard ;
        colorcard = card.stream().filter(card1 -> card1.getColor().equals(cardColor)).collect(Collectors.toSet());
        if (card.size() >= 3) {
            return new ResponseEntity<>("You cannot have more than three cards", HttpStatus.FORBIDDEN);
        } else if(colorcard.size() >= 1){
            return new ResponseEntity<>("You cannot have two identical cards", HttpStatus.FORBIDDEN);
         }
        String auxCardNumber;
            do {
                auxCardNumber = CardUtils.getCardNumber();
            }while (cardRepository.findByNumber(auxCardNumber) != null);

        int cvv = CardUtils.getCvv();
        Card newCard = new Card(cardType , cardColor,auxCardNumber, cvv, LocalDate.now(), LocalDate.now().plusYears(5),client);
        cardRepository.save(newCard);
        client.addCard(newCard);
        clientService.saveClient(client);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> deleteCard(@RequestParam String cardNumber, Authentication authentication) {

            if (authentication.getName() == null) {
                return new ResponseEntity<>("not autheticated client", HttpStatus.FORBIDDEN);
            }
            String auxCar= cardNumber;

            if (cardNumber.isEmpty()) {
                return new ResponseEntity<>("Missing card data", HttpStatus.FORBIDDEN);
            }
            Client client = clientService.findByEmail(authentication.getName());
            Card card = cardService.findByNumber(cardNumber);
            if (client.getCards().stream().noneMatch(card1 -> card1.getNumber().equals(cardNumber))) {
                return new ResponseEntity<>("This card is not yours", HttpStatus.FORBIDDEN);
            }
            card.setActive(false);
            cardService.saveCard(card);
            return new ResponseEntity<>("Card deleted", HttpStatus.OK);
        }

}
