package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /**
     * This first test see the behaviour in front of player clockwise rotation, and player removal.
     */
    @Test
    void setupCreateRemoveNextPlayer() throws DuplicateNicknameException, DuplicateColorException {
        Game testGame = new Game();
        /*testGame.createNewPlayer(new Player("piro", PlayerColors.RED));

        assertThrows(DuplicateColorException.class, ()->testGame.createNewPlayer(new Player("pino", PlayerColors.RED)));
        assertThrows(DuplicateNicknameException.class, ()->testGame.createNewPlayer(new Player("piro", PlayerColors.GREEN)));

        testGame.createNewPlayer(new Player("alice", PlayerColors.GREEN));
        testGame.createNewPlayer(new Player("nico", PlayerColors.BLUE));

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
        assertEquals(testGame.getCurrentPlayer().getNickname(), "alice");*/
    }

    @Test
    void cardChoice() {
    }

    @Test
    void setup() {
    }
}