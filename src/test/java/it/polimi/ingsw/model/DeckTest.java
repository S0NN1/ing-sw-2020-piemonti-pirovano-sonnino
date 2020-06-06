package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class DeckTest tests Deck.
 * @author Luca Pirovano
 * @see Deck
 */
class DeckTest {
    Game testGame;
    Deck testDeck;

    /**
     * Method Initialization creates a new Game, a Deck and 2 test players, with the god powers of Atlas and Athena.
     * The current player is then set as "Luca".
     */
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

    /**
     * Method insertNormalTest tests deck insertion in normal conditions.
     */
    @Test
    @DisplayName("Insert test in normal conditions")
    void insertNormalTest() {
        assertEquals(Card.ATLAS, testDeck.getCards().get(0));
        assertEquals(Card.ATHENA, testDeck.getCards().get(1));
    }

    /**
     * Method insertWithDuplicate tests deck with a duplicate card insertion. A false return (value 0) is expected.
     */
    @Test
    @DisplayName("Insert test with duplicate card")
    void insertWithDuplicate() throws OutOfBoundException {
        testGame.createNewPlayer(new Player("Sonny", 2));
        testGame.getActivePlayers().get(2).setColor(PlayerColors.BLUE);
        assertEquals(0, testDeck.setCard(Card.ATLAS));
    }

    /**
     * Method insertOutOfBound tests deck with an extra-player card insertion (number of god cards > number of players).
     * An "OutOfBoundException" is expected when trying to insert another card.
     */
    @Test
    @DisplayName("Insert test with OutOfBound throwing")
    void insertOutOfBound() {
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
    }

    /**
     * Method insertThreePlayers tests the same as above with 3 players instead of 2.
     */
    @Test
    @DisplayName("Insert test with 3 players in the two conditions above")
    void insertThreePlayers() throws OutOfBoundException {
        testGame.createNewPlayer(new Player("Sonny", 2));
        testGame.getActivePlayers().get(2).setColor(PlayerColors.BLUE);
        testDeck.setCard(Card.APOLLO);
        List<Card> cards = testDeck.getCards();
        assertEquals( Card.ATLAS, cards.get(0));
        assertEquals(Card.ATHENA, cards.get(1));
        assertEquals(Card.APOLLO, cards.get(2));
        assertThrows(OutOfBoundException.class, ()->testDeck.setCard(Card.ARTEMIS));
    }

    /**
     * Method removeCardTest tests choice procedure test in standard working conditions.
     */
    @Test
    @DisplayName("Choose test in normal conditions")
    void removeCardTest() {
        assertTrue(testDeck.chooseCard(Card.ATLAS, null));
        assertEquals(1, testDeck.getCards().size());
        testGame.nextPlayer();
        assertTrue(testDeck.chooseCard(Card.ATHENA, null, null));
        assertEquals(0, testDeck.getCards().size());
    }

    /**
     * Method removeCardNotChosenTest tests choice procedure test with a selection of a card which was not chosen by the
     * challenger. A false return of the choose function is expected.
     */
    @Test
    @DisplayName("Choose test with not chosen card")
    void removeCardNotChosenTest() {
        assertTrue(testDeck.chooseCard(Card.ATLAS, null));
        assertFalse(testDeck.chooseCard(Card.APOLLO, null));
    }
}