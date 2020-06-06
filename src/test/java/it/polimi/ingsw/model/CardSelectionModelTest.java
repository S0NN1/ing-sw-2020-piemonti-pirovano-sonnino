package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class CardSelectionModelTest tests CardSelectionModel class.
 * @author Luca Pirovano
 * @see CardSelectionModel
 */
class CardSelectionModelTest {

    CardSelectionModel testModel;
    Game game;

    /**
     * Method setup setups test with the creation of 2 players.
     */
    @BeforeEach
    void setup() {
        game = new Game();
        testModel = new CardSelectionModel(game.getDeck());
        game.createNewPlayer(new Player("Luca", 0));
        game.createNewPlayer(new Player("Alice", 1));
        game.getActivePlayers().get(0).setColor(PlayerColors.RED);
        game.getActivePlayers().get(1).setColor(PlayerColors.GREEN);
    }

    /**
     * Method testInsertStandard tests insertion with standard conditions.
     * @throws OutOfBoundException when addition to deck fails.
     */
    @Test
    @DisplayName("Insert test in standard conditions")
    void testInsertStandard() throws OutOfBoundException{
        testModel.addToDeck(Card.PROMETHEUS);
        testModel.addToDeck(Card.APOLLO);
        assertEquals(2, game.getDeck().getCards().size());
        assertEquals(Card.PROMETHEUS, game.getDeck().getCards().get(0));
        assertEquals(Card.APOLLO, game.getDeck().getCards().get(1));
    }

    /**
     * Method testInsertionDuplicate tests insertion with duplicate values check.
     * @throws OutOfBoundException when card is invalid.
     */
    @Test
    @DisplayName("Insert test with duplicate values")
    void testInsertDuplicate() throws OutOfBoundException {
        testModel.addToDeck(Card.ATHENA);
        assertEquals(1, game.getDeck().getCards().size());
        assertEquals(Card.ATHENA, game.getDeck().getCards().get(0));
        testModel.addToDeck(Card.ATHENA);
        assertEquals(1, game.getDeck().getCards().size());
        assertEquals(Card.ATHENA, game.getDeck().getCards().get(0));
        testModel.addToDeck(Card.PAN);
        assertEquals(2, game.getDeck().getCards().size());
        assertEquals(Card.PAN, game.getDeck().getCards().get(1));
    }

    /**
     * Method testInsertOutOfBound tests insertion with "out of bound" cards condition.
     * @throws OutOfBoundException when addition to deck fails.
     */
    @Test
    @DisplayName("Insert test with out of bound cards")
    void testInsertOutOfBound() throws OutOfBoundException {
        testModel.addToDeck(Card.APOLLO);
        testModel.addToDeck(Card.ATHENA);
        assertThrows(OutOfBoundException.class, () -> testModel.addToDeck(Card.PROMETHEUS));
    }

    /**
     * Method setGodTest tests the setting of the selected god.
     */
    @Test
    @DisplayName("Setting of the selected god")
    void setGodTest() {
        for(Card card:Card.values()) {
            testModel.setSelectedGod(card);
            assertEquals(testModel.getSelectedGod(), card);
        }
    }
}