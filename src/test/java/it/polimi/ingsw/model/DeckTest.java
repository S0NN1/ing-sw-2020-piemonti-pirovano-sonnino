package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CardNotChosenException;
import it.polimi.ingsw.exceptions.DuplicateGodException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayersNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck testDeck;

    @BeforeEach
    void initialization() {
        testDeck = new Deck();
    }

    @Test
    void setCardTest1() throws OutOfBoundException, DuplicateGodException {
        PlayersNumber.setPlayer(2);
        testDeck.setCard(Card.APOLLO);
        assertThrows(DuplicateGodException.class, ()->testDeck.setCard(Card.APOLLO));
        testDeck.setCard(Card.ATHENA);
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
    }

    @Test
    void setCardTest2() throws OutOfBoundException, DuplicateGodException {
        PlayersNumber.setPlayer(3);
        testDeck.setCard(Card.APOLLO);
        assertThrows(DuplicateGodException.class, ()->testDeck.setCard(Card.APOLLO));
        testDeck.setCard(Card.ATHENA);
        testDeck.setCard(Card.ATLAS);
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
        ArrayList<Card> cards = testDeck.getCards();
        assertEquals(cards.get(0), Card.APOLLO);
        assertEquals(cards.get(1), Card.ATHENA);
        assertEquals(cards.get(2), Card.ATLAS);

    }

    @Test
    void removeCardTest() throws OutOfBoundException, DuplicateGodException, CardNotChosenException {
        PlayersNumber.setPlayer(3);
        testDeck.setCard(Card.APOLLO);
        testDeck.setCard(Card.ATHENA);
        testDeck.setCard(Card.ATLAS);
        testDeck.removeCard(Card.ATLAS);
        assertEquals(testDeck.getCards().size(), 2);
        assertThrows(CardNotChosenException.class, () -> testDeck.removeCard(Card.ATLAS));
        testDeck.removeCard(Card.ATHENA);
        assertThrows(CardNotChosenException.class, () -> testDeck.removeCard(Card.ATHENA));
        testDeck.removeCard(Card.APOLLO);
        assertEquals(testDeck.getCards().size(), 0);
    }


}