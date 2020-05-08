package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game testGame;

    @BeforeEach
    void initialization() {
        testGame = new Game();
        testGame.createNewPlayer(new Player("piro", 0));
        testGame.createNewPlayer(new Player("alice", 1));
        testGame.createNewPlayer(new Player("nico", 2));
    }

    /**
     * This first test see the behaviour in front of player clockwise rotation, and player removal.
     */
    @Test
    @DisplayName("Player clockwise rotation test")
    void setupCreateRemoveNextPlayer() {
        testGame.setCurrentPlayer(testGame.getActivePlayers().get(0));
        assertEquals("piro", testGame.getCurrentPlayer().getNickname());
        testGame.nextPlayer();
        assertEquals("alice", testGame.getCurrentPlayer().getNickname());
        testGame.nextPlayer();
        assertEquals("nico", testGame.getCurrentPlayer().getNickname());

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
     * This test aims to verify the correctness of the player information getters, like the nickname and the ID ones.
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