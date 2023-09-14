package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
    @Override
    public Card findByNumber(String string) {
        return cardRepository.findByNumber(string);
    }

    @Override
    public Card findById(Long id){
        return cardRepository.findById(id).get();
    }
}
