package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.PlayerColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class TritonTest tests class Triton.
 *
 * @author Alice Piemonti
 * @see Triton
 */
public class TritonTest {

    Triton triton;
    GameBoard gameBoard;

    /**
     * Method init initializes worker with a Triton instance, a new gameBoard and Triton's starting position.
     */
    @BeforeEach
    void init() {
        triton = new Triton(PlayerColors.RED);
        gameBoard = new GameBoard();
        triton.setPosition(gameBoard.getSpace(1,1));
    }

    /**
     * Method movePerimeterTest tests god's power: it can move another time each time it moves into a perimeter space.
     */
    @Test
    @DisplayName("move on a perimeter space")
    void movePerimeterTest() {
        Space move = gameBoard.getSpace(0,1);

        assertTrue(triton.getPhase(1).isMust(),"1");
        triton.notifyWithMoves(gameBoard);

        //move on a perimeter space adds another move
        triton.move(move);

        assertEquals(Action.SELECT_MOVE, triton.getPhase(2).getAction(),"2");
        assertFalse(triton.getPhase(2).isMust(),"3");
        assertEquals(Action.MOVE, triton.getPhase(3).getAction(),"4");
        assertFalse(triton.getPhase(3).isMust(),"5");
        assertEquals(Action.SELECT_BUILD, triton.getPhase(4).getAction(), "5a");
        assertTrue(triton.getPhase(4).isMust(),"5b");

        triton.notifyWithMoves(gameBoard);
        assertTrue(triton.getPhase(3).isMust(),"6");
        triton.move(gameBoard.getSpace(0,0));

        //test reset
        triton.notifyWithBuildable(gameBoard);
        triton.build(gameBoard.getSpace(0,1));
        assertEquals(Action.SELECT_MOVE, triton.getPhase(0).getAction(),"7");
        assertEquals(Action.SELECT_BUILD, triton.getPhase(2).getAction(),"8");
        assertEquals(Action.BUILD, triton.getPhase(3).getAction(),"9");
    }

    /**
     * Method normalMoveTest tests the normal behavior of a move and a build.
     */
    @Test
    @DisplayName("normal move")
    void normalMoveTest() {
        Space move = gameBoard.getSpace(2,2);

        triton.notifyWithMoves(gameBoard);
         //move on a not perimeter space does nothing
        triton.move(move);

        assertEquals(Action.SELECT_BUILD, triton.getPhase(2).getAction(),"2");
        assertTrue(triton.getPhase(2).isMust(),"3");
        assertEquals(Action.BUILD, triton.getPhase(3).getAction(),"4");
        assertTrue(triton.getPhase(3).isMust(),"5");
    }

    /**
     * Method multipleMovesTest tests the behavior of a sequence of moves into perimeter spaces.
     */
    @Test
    @DisplayName("sequence moves")
    void multipleMovesTest() {
        Space move;
        for ( int i = 0; i < Constants.GRID_MAX_SIZE; i++) {
            move = gameBoard.getSpace(0, i);
            triton.notifyWithMoves(gameBoard);
            triton.move(move);

            assertEquals(Action.SELECT_MOVE, triton.getPhase(2*i + 2).getAction(), "1."+ i);
            assertFalse(triton.getPhase(2*i + 2).isMust(),"2." + i);
            assertEquals(Action.SELECT_BUILD, triton.getPhase(2*i + 4).getAction(), "3." + i);
            assertTrue(triton.getPhase(2*i + 4).isMust(),"4."+ i);
        }

        triton.build(gameBoard.getSpace(1,4)); //reset values
        for (int j = 0; j < Constants.GRID_MAX_SIZE; j++) {
            move = gameBoard.getSpace(j, 4);
            triton.notifyWithMoves(gameBoard);
            triton.move(move);

            assertEquals(Action.SELECT_MOVE, triton.getPhase(2*j + 2).getAction(), "5."+ j);
            assertFalse(triton.getPhase(2*j + 2).isMust(),"6." + j);
            assertEquals(Action.SELECT_BUILD, triton.getPhase(2*j + 4).getAction(), "7." + j);
            assertTrue(triton.getPhase(2*j + 4).isMust(),"8."+ j);
        }
    }
}
