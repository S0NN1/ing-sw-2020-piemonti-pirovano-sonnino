package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.VirtualClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class GameTest tests Game class.
 *
 * @author Luca Pirovano
 * @see Game
 */
class GameTest {

    Game testGame;

    /**
     * Method initialization initializes values.
     */
    @BeforeEach
    void initialization() {
        testGame = new Game();
        testGame.createNewPlayer(new Player("piro", 0));
        testGame.createNewPlayer(new Player("alice", 1));
        testGame.createNewPlayer(new Player("nico", 2));
    }

    /**
     * Method setupCreatRemoveNextPlayer tests the behaviour in front of player clockwise rotation, and player removal.
     */
    @Test
    @DisplayName("Player clockwise rotation test")
    void setupCreateRemoveNextPlayer() {
        testGame.setCurrentPlayer(testGame.getActivePlayers().get(0));
        assertEquals("piro", testGame.getCurrentPlayer().getNickname());
        testGame.getCurrentPlayer().setColor(PlayerColors.GREEN);
        testGame.getCurrentPlayer().addWorker(Card.APOLLO, new VirtualClient());
        testGame.getGameBoard().getSpace(0, 1).setWorker(testGame.getCurrentPlayer().getWorkers().get(0));
        testGame.getGameBoard().getSpace(1, 1).setWorker(testGame.getCurrentPlayer().getWorkers().get(1));
        testGame.nextPlayer();
        assertEquals("alice", testGame.getCurrentPlayer().getNickname());
        testGame.getCurrentPlayer().setColor(PlayerColors.RED);
        testGame.getCurrentPlayer().addWorker(Card.PROMETHEUS, new VirtualClient());
        testGame.getGameBoard().getSpace(2, 3).setWorker(testGame.getCurrentPlayer().getWorkers().get(0));
        testGame.getGameBoard().getSpace(4, 4).setWorker(testGame.getCurrentPlayer().getWorkers().get(1));
        testGame.nextPlayer();
        assertEquals("nico", testGame.getCurrentPlayer().getNickname());
        testGame.getCurrentPlayer().setColor(PlayerColors.BLUE);
        testGame.getCurrentPlayer().addWorker(Card.ATLAS, new VirtualClient());
        testGame.getGameBoard().getSpace(2, 2).setWorker(testGame.getCurrentPlayer().getWorkers().get(0));
        testGame.getGameBoard().getSpace(3, 3).setWorker(testGame.getCurrentPlayer().getWorkers().get(1));

        testGame.nextPlayer();
        testGame.removePlayer(testGame.getPlayerByNickname("piro"));
        assertEquals("alice", testGame.getCurrentPlayer().getNickname());

        testGame.nextPlayer();
        assertEquals("nico", testGame.getCurrentPlayer().getNickname());
        testGame.nextPlayer();
        assertEquals("alice", testGame.getCurrentPlayer().getNickname());

        assertNull(testGame.getPlayerByNickname("lalo"));

        testGame.removePlayer(testGame.getPlayerByNickname("nico"));
        testGame.removePlayer(testGame.getPlayerByNickname("alice"));
        assertTrue(testGame.getActivePlayers().isEmpty());
    }

    /**
     * Method playerGettingByIdTest aims to verify the correctness of the player information getters, like the nickname
     * and the ID ones.
     */
    @Test
    @DisplayName("Player getById and getByNickname method binding test")
    void playerGettingByIdTest() {
        assertEquals(testGame.getPlayerByID(0), testGame.getPlayerByNickname("piro"));
        assertEquals(testGame.getPlayerByID(1), testGame.getPlayerByNickname("alice"));
        assertEquals(testGame.getPlayerByID(2), testGame.getPlayerByNickname("nico"));
        assertNotEquals(testGame.getPlayerByID(0), testGame.getPlayerByNickname("nico"));
        assertNull(testGame.getPlayerByID(5));
        assertNull(testGame.getPlayerByNickname("ugo"));
    }
}