package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class ArtemisTest {

    Worker artemis;
    GameBoard gameBoard;

    @BeforeEach
    void init() throws InvalidInputException {
        artemis = new Artemis(PlayerColors.RED);
        gameBoard = new GameBoard();
        artemis.setPosition(gameBoard.getSpace(1,1));
    }

    /**
     * test getPhase.isMust parameter during the succession of a move and build action in a single turn
     */
    @Test
    @DisplayName("one move one build")
    void oneNormalMove() {
        //first turn
        assertTrue(artemis.getPhase(0).isMust(),"1"); //1,1

        Space move = artemis.selectMoves(gameBoard).get(2); //select a move
        assertTrue(artemis.getPhase(1).isMust(),"2"); //first move is a must

        assertTrue(artemis.isSelectable(move),"3");
        assertTrue(artemis.move(move),"4");

        assertFalse(artemis.getPhase(2).isMust(),"4a");
        assertFalse(artemis.getPhase(3).isMust(),"4b");
        assertTrue(artemis.getPhase(4).isMust(),"4c");
        artemis.notifyWithBuildable(gameBoard);

        //second turn
        assertTrue(artemis.getPhase(0).isMust(),"5");
        move = artemis.selectMoves(gameBoard).get(1);
        assertTrue(artemis.getPhase(1).isMust(),"6");

        assertTrue(artemis.isSelectable(move),"7");
        assertTrue(artemis.move(move),"8");

        assertFalse(artemis.getPhase(2).isMust(),"9a");
        assertFalse(artemis.getPhase(3).isMust(),"9b");
        assertTrue(artemis.getPhase(4).isMust(),"9c");
        artemis.notifyWithBuildable(gameBoard);
    }

    /**
     * test getPhase.isMust parameter during the succession of a move and build action in a single turn
     */
    @Test
    @DisplayName("one move one build but return to the previous position")
    void oneMoveWithReturn(){
        //first turn - position = 1,1
        Space move = artemis.selectMoves(gameBoard).get(2); //select a move

        assertTrue(artemis.isSelectable(move),"1");
        assertTrue(artemis.move(move),"2");

        artemis.notifyWithBuildable(gameBoard);

        //second turn - return to 1,1
        move = gameBoard.getSpace(1,1);
        assertTrue(artemis.selectMoves(gameBoard).contains(move),"3");

        assertTrue(artemis.isSelectable(move),"4");
        assertTrue(artemis.move(move),"5");
    }

    /**
     * test getPhase.isMust parameter during the succession of two moves and one build action in a single turn
     * the second move is correct: not to the previous position
     */
    @Test
    @DisplayName("two correct moves")
    void twoCorrectMoves(){
        //first turn - first move
        Space move = artemis.selectMoves(gameBoard).get(2); //select a move

        assertTrue(artemis.isSelectable(move),"1");
        assertTrue(artemis.move(move),"2");

        //first turn - second move
        assertFalse(artemis.getPhase(2).isMust(),"3");
        move = artemis.selectMoves(gameBoard).get(1);

        assertTrue(artemis.getPhase(3).isMust(),"4"); //second move is now a must
        assertTrue(artemis.isSelectable(move),"4a");
        assertTrue(artemis.move(move),"4b");

        assertTrue(artemis.getPhase(4).isMust(),"5");
        artemis.notifyWithBuildable(gameBoard);

        //second turn

        assertTrue(artemis.getPhase(0).isMust(),"5b");
        move = artemis.selectMoves(gameBoard).get(1);
        assertTrue(artemis.getPhase(1).isMust(),"6");

        assertTrue(artemis.isSelectable(move),"7");
        assertTrue(artemis.move(move),"8");

        assertFalse(artemis.getPhase(2).isMust(),"9a");
        assertFalse(artemis.getPhase(3).isMust(),"9b");
        assertTrue(artemis.getPhase(4).isMust(),"9c");

    }

    /**
     * test getPhase.isMust parameter during the succession of two moves and one build action in a single turn
     * the second move is wrong: try to move to the previous position
     */
    @Test
    @DisplayName("try to return to initial space")
    void twoWrongMoves(){
        //first turn - first move - position = 1,1
        Space move = gameBoard.getSpace(2,2); //select a move

        assertTrue(artemis.isSelectable(move),"1");
        assertTrue(artemis.move(move),"2");

        //first turn - second move
        move = gameBoard.getSpace(1,1);
        artemis.selectMoves(gameBoard); //select second move
        assertTrue(artemis.getPhase(3).isMust(),"4"); //second move is now a must

        assertFalse(artemis.isSelectable(move),"4a"); //try to return to 1,1
        assertFalse(artemis.move(move),"4b");

        assertTrue(artemis.getPhase(3).isMust(),"5");
        move = gameBoard.getSpace(1,2); //select another move

        assertTrue(artemis.isSelectable(move),"6");
        assertTrue(artemis.move(move),"7");
    }
}