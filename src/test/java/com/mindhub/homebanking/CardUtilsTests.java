package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    public CardUtilsTests() {
    }
    @Test

    public void cardNumberIsCreated(){

        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
    @Test
    public void cardNumberLength(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(String.valueOf(cardNumber).length(),is(19));
    }
    @Test
    public void cvvIsCreated(){
        int cvv = CardUtils.getCvv();
        assertThat(String.valueOf(cvv).length(), is(3));
    }
    @Test
    public void checkCvvLength() {
        int cvv = CardUtils.getCvv();
        assertThat(cvv, is(not(equals(3))));
    }
}
