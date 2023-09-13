package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public final class CardUtils {
    @Autowired
    private static CardRepository cardRepository;
    private CardUtils() {
    }
    public static String getCardNumber() {
        String cardNumber = (int) ((Math.random() * 10000)) + " " + (int) ((Math.random() * 10000)) + " " + (int) ((Math.random() * 10000)) + " " + (int) ((Math.random() * 10000));
        return cardNumber;
    }

    public static int getCvv() {
        int cvv=(int) ((Math.random() * 1000));
        return cvv;
    }
}
