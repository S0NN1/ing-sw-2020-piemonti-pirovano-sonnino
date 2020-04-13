package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Game testGame;
    Deck testDeck;

    @BeforeEach
    void initialization() throws OutOfBoundException {
        testGame = new Game();
        testDeck = new Deck(testGame);
        testGame.createNewPlayer(new Player("Luca", 0));
        testGame.createNewPlayer(new Player("Alice", 1));
        testGame.getActivePlayers().get(0).setColor(PlayerColors.RED);
        testGame.getActivePlayers().get(1).setColor(PlayerColors.GREEN);
        testGame.setCurrentPlayer(testGame.getActivePlayers().get(0));
        testDeck.setCard(Card.ATLAS);
        testDeck.setCard(Card.ATHENA);
    }

    @Test
    @DisplayName("Insert test in normal conditions")
    void insertNormalTest() {
        assertEquals(testDeck.getCards().get(0), Card.ATLAS);
        assertEquals(testDeck.getCards().get(1), Card.ATHENA);
    }

    @Test
    @DisplayName("Insert test with duplicate card")
    void insertWithDuplicate() throws OutOfBoundException {
        testGame.createNewPlayer(new Player("Sonny", 2));
        testGame.getActivePlayers().get(2).setColor(PlayerColors.BLUE);
        assertEquals(testDeck.setCard(Card.ATLAS), 0);
    }

    @Test
    @DisplayName("Insert test with OutOfBound throwing")
    void insertOutOfBound() {
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
    }

    @Test
    @DisplayName("Insert test with 3 players in the two conditions above")
    void insertThreePlayers() throws OutOfBoundException {
        testGame.createNewPlayer(new Player("Sonny", 2));
        testGame.getActivePlayers().get(2).setColor(PlayerColors.BLUE);
        testDeck.setCard(Card.APOLLO);
        ArrayList<Card> cards = testDeck.getCards();
        assertEquals(cards.get(0), Card.ATLAS);
        assertEquals(cards.get(1), Card.ATHENA);
        assertEquals(cards.get(2), Card.APOLLO);
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
    }

    @Test
    @DisplayName("Choose test in normal conditions")
    void removeCardTest() {
        assertTrue(testDeck.chooseCard(Card.ATLAS));
        assertEquals(testDeck.getCards().size(), 1);
        testGame.nextPlayer();
        assertTrue(testDeck.chooseCard(Card.ATHENA));
        assertEquals(testDeck.getCards().size(), 0);
    }

    @Test
    @DisplayName("Choose test with not chosen card")
    void removeCardNotChosenTest() {
        assertTrue(testDeck.chooseCard(Card.ATLAS));
        assertFalse(testDeck.chooseCard(Card.APOLLO));
    }


}