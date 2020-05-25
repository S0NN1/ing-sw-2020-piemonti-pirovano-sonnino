package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.PlayerColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class TritonTest ...
 *
 * @author Alice Piemonti
 * Created on 25/05/2020
 */
public class TritonTest {

    Triton triton;
    GameBoard gameBoard;

    @BeforeEach
    void init() {
        triton = new Triton(PlayerColors.RED);
        gameBoard = new GameBoard();
        triton.setPosition(gameBoard.getSpace(1,1));
    }

    @Test
    @DisplayName("move on a perimeter space")
    void movePerimeter() {
        Space move = gameBoard.getSpace(0,1);

        assertTrue(triton.getPhase(1).isMust(),"1");
        triton.notifyWithMoves(gameBoard);

        //move on a perimeter space adds another move
        triton.move(move);

        assertEquals(Action.SELECTMOVE, triton.getPhase(2).getAction(),"2");
        assertFalse(triton.getPhase(2).isMust(),"3");
        assertEquals(Action.MOVE, triton.getPhase(3).getAction(),"4");
        assertFalse(triton.getPhase(3).isMust(),"5");
    }

    @Test
    @DisplayName("normal move")
    void normalMove() {
        Space move = gameBoard.getSpace(2,2);

        triton.notifyWithMoves(gameBoard);
         //move on a not perimeter space does nothing
        triton.move(move);

        assertEquals(Action.SELECTBUILD, triton.getPhase(2).getAction(),"2");
        assertTrue(triton.getPhase(2).isMust(),"3");
        assertEquals(Action.BUILD, triton.getPhase(3).getAction(),"4");
        assertTrue(triton.getPhase(3).isMust(),"5");
    }
}
