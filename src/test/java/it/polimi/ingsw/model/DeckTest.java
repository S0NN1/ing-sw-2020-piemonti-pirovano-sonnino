package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.PlayersNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck testDeck;

    @BeforeEach
    void initialization() {
        testDeck = new Deck(new Game());
    }

    @Test
    void setCardTest1() throws OutOfBoundException {
        PlayersNumber.setPlayer(2);
        testDeck.setCard(Card.APOLLO);
        testDeck.setCard(Card.ATHENA);
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
    }

    @Test
    void setCardTest2() throws OutOfBoundException {
        PlayersNumber.setPlayer(3);
        testDeck.setCard(Card.APOLLO);
        testDeck.setCard(Card.ATHENA);
        testDeck.setCard(Card.ATLAS);
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
        ArrayList<Card> cards = testDeck.getCards();
        assertEquals(cards.get(0), Card.APOLLO);
        assertEquals(cards.get(1), Card.ATHENA);
        assertEquals(cards.get(2), Card.ATLAS);

    }

    @Test
    void removeCardTest() throws OutOfBoundException {
        PlayersNumber.setPlayer(3);
        testDeck.setCard(Card.APOLLO);
        testDeck.setCard(Card.ATHENA);
        testDeck.setCard(Card.ATLAS);
        testDeck.chooseCard(Card.ATLAS);
        assertEquals(testDeck.getCards().size(), 2);
        testDeck.chooseCard(Card.ATHENA);
        testDeck.chooseCard(Card.APOLLO);
        assertEquals(testDeck.getCards().size(), 0);
    }


}