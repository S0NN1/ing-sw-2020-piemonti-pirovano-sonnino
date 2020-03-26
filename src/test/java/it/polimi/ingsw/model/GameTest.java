package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void setupRemoveNextPlayer() throws DuplicateNicknameException, DuplicateColorException {
        Game testGame = new Game();
        testGame.createNewPlayer(new Player("piro", PlayerColors.WHITE));

        assertThrows(DuplicateColorException.class, ()->testGame.createNewPlayer(new Player("pino", PlayerColors.WHITE)));
        assertThrows(DuplicateNicknameException.class, ()->testGame.createNewPlayer(new Player("piro", PlayerColors.GREY)));

        testGame.createNewPlayer(new Player("alice", PlayerColors.GREY));
        testGame.createNewPlayer(new Player("sonny", PlayerColors.BLUE));

        testGame.setCurrentPlayer(testGame.getActivePlayers().get(0));
        System.out.println(testGame.getCurrentPlayer().getNickname());
        testGame.nextPlayer();
        System.out.println(testGame.getCurrentPlayer().getNickname());
        testGame.nextPlayer();
        System.out.println(testGame.getCurrentPlayer().getNickname());

        testGame.nextPlayer();
        testGame.removePlayer(testGame.getCurrentPlayer());
        System.out.println(testGame.getCurrentPlayer().getNickname());

        testGame.nextPlayer();
        System.out.println(testGame.getCurrentPlayer().getNickname());
        testGame.nextPlayer();
        System.out.println(testGame.getCurrentPlayer().getNickname());
    }


    @Test
    void createNewPlayer() {
    }

    /*@Test
    void getSpace() {
        Game board = new Game();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> board.getSpace(3,7), "An out of bound exception should be thrown.");
    }*/

    @Test
    void cardChoice() {
    }

    @Test
    void setup() {
    }
}