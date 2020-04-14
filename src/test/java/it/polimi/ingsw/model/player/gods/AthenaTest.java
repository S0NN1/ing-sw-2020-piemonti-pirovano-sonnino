package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class AthenaTest {

    @Test
    @DisplayName("test move up")
    void moveUpTest() throws OutOfBoundException {

        Worker athena = new Athena(PlayerColors.BLUE);
        GameBoard gameBoard = new GameBoard();

        Space firstPosition = gameBoard.getSpace(1,1);
        Space secondPosition = gameBoard.getSpace(1,2);
        athena.setPosition(firstPosition);
        secondPosition.getTower().addLevel();

        assertTrue(athena.isSelectable(secondPosition));
        athena.move(secondPosition); //test with debugger

    }

}