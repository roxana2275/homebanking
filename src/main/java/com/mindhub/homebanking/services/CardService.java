package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {
    Card findByNumber(String string);
    void saveCard(Card card);
    Card findById(Long id);
}
