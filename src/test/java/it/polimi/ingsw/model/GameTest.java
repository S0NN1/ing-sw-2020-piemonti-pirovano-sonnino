package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.PlayersNumber;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void selectionTest() {
        Game testGame = new Game();
        testGame.createNewPlayer(new Player("piro", PlayerColors.BLUE));
        testGame.createNewPlayer(new Player("piro", PlayerColors.WHITE));
        PlayersNumber.setPlayer(2);
        testGame.createDeck();
        ByteArrayInputStream in = new ByteArrayInputStream("desc apollo".getBytes());
        System.setIn(in);
    }
}