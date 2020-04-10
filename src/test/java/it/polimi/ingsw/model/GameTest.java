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
    @DisplayName("Player setup, removal and clockwise rotation.")
    void setupCreateRemoveNextPlayer() {
        testGame.setCurrentPlayer(testGame.getActivePlayers().get(0));
        assertEquals(testGame.getCurrentPlayer().getNickname(), "piro");
        testGame.nextPlayer();
        assertEquals(testGame.getCurrentPlayer().getNickname(), "alice");
        testGame.nextPlayer();
        assertEquals(testGame.getCurrentPlayer().getNickname(), "nico");

        testGame.nextPlayer();
        testGame.removePlayer(testGame.getPlayerByNickname("piro"));
        assertEquals(testGame.getCurrentPlayer().getNickname(), "alice");

        testGame.nextPlayer();
        assertEquals(testGame.getCurrentPlayer().getNickname(), "nico");
        testGame.nextPlayer();
        assertEquals(testGame.getCurrentPlayer().getNickname(), "alice");

        assertNull(testGame.getPlayerByNickname("lalo"));

        testGame.removePlayer(testGame.getPlayerByNickname("nico"));
        testGame.removePlayer(testGame.getPlayerByNickname("alice"));
        assertTrue(testGame.getActivePlayers().isEmpty());
    }

    @Test
    void cardChoice() {
    }

    @Test
    void setup() {
    }
}