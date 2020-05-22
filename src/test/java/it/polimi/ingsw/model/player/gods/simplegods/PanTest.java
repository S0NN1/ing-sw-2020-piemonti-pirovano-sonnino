package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.simplegods.Pan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class PanTest {

    Worker pan;
    GameBoard gameBoard;

    @BeforeEach
    void init(){
        pan = new Pan(PlayerColors.BLUE);
        gameBoard = new GameBoard();
    }

    /**
     * test the method winCondition: Pan must win either when moves down two or more levels
     * @throws OutOfBoundException
     */
    @Test
    @DisplayName("win condition true")
    void moveTest() throws OutOfBoundException {
        Space space = gameBoard.getSpace(1,1);
        Space space0 = gameBoard.getSpace(1,2);
        Space space1 = gameBoard.getSpace(2,2);
        Space space2 = gameBoard.getSpace(2,1);

        space1.getTower().addLevel();
        space2.getTower().addLevel();
        space2.getTower().addLevel();

        pan.setPosition(space); //lv 0 -> 0
        pan.move(space0);
        assertFalse(pan.winCondition(space),"1");

        space.getTower().addLevel();
        pan.setPosition(space); //lv 1 -> 0
        pan.move(space0);
        assertFalse(pan.winCondition(space),"2");

        space.getTower().addLevel();
        pan.setPosition(space); //lv 2 -> 0
        pan.move(space0);
        assertTrue(pan.winCondition(space),"3");
        pan.setPosition(space);
        pan.move(space1); //lv 2 -> 1
        assertFalse(pan.winCondition(space),"4");

        space.getTower().addLevel();
        pan.setPosition(space); //lv 3 -> 0
        pan.move(space0);
        assertTrue(pan.winCondition(space),"5");
        pan.setPosition(space);
        pan.move(space1);//lv 3 -> 1
        assertTrue(pan.winCondition(space),"6");
        pan.setPosition(space);
        pan.move(space2); //lv 3 -> 2
        assertFalse(pan.winCondition(space),"7");
    }

}