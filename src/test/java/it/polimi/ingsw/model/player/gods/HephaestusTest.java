package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class HephaestusTest {

    Worker hephaestus;
    GameBoard gameBoard;
    Space build;

    @BeforeEach
    void init(){
        hephaestus = new Hephaestus(PlayerColors.GREEN);
        gameBoard = new GameBoard();
        hephaestus.setPosition(gameBoard.getSpace(1,1));
        build = gameBoard.getSpace(2,2);
    }

    /**
     * test getPhase.isMust parameter during the succession of one move and one build action in a single turn
     */
    @Test
    @DisplayName("one move one build")
    void oneNormalBuild(){
        //first turn
        hephaestus.notifyWithMoves(gameBoard);
        assertTrue(hephaestus.getPhase(2).isMust(),"1"); //1,1

        hephaestus.getBuildableSpaces(gameBoard); //select build
        assertTrue(hephaestus.getPhase(3).isMust(),"2"); //first build is a must

        assertTrue(hephaestus.isBuildable(build),"3"); //build = 2,2
        assertTrue(hephaestus.build(build),"4");

        assertFalse(hephaestus.getPhase(4).isMust(),"4a");
        assertFalse(hephaestus.getPhase(5).isMust(),"4b");

        //second turn
        hephaestus.notifyWithMoves(gameBoard); //restart oldPosition value
        assertTrue(hephaestus.getPhase(2).isMust(),"5");
        hephaestus.getBuildableSpaces(gameBoard);
        assertTrue(hephaestus.getPhase(3).isMust(),"6");

        build = gameBoard.getSpace(2,1);
        assertTrue(hephaestus.isBuildable(build),"7"); //try to build on another space = 2,1
        assertTrue(hephaestus.build(build),"8");

        assertFalse(hephaestus.getPhase(4).isMust(),"9a");
        assertFalse(hephaestus.getPhase(5).isMust(),"9b");
    }


    /**
     * test getPhase.isMust parameter during the succession of one move and two build actions in a single turn
     */
    @Test
    @DisplayName("one move one build but build on another space")
    void oneBuildInTheSame(){
        //first turn - build = 2,2
        hephaestus.getBuildableSpaces(gameBoard); //select a build

        assertTrue(hephaestus.isBuildable(build),"1");
        assertTrue(hephaestus.build(build),"2");

        //second turn - build in 1,2
        hephaestus.notifyWithMoves(gameBoard); //restart oldPosition
        build = gameBoard.getSpace(1,2);
        assertTrue(hephaestus.getBuildableSpaces(gameBoard).contains(build),"3");

        assertTrue(hephaestus.isBuildable(build),"4");
        assertTrue(hephaestus.build(build),"5");
    }


    /**
     * test getPhase.isMust parameter during the succession of one move and two build actions in a single turn
     * the second build is correct: build on the previous space
     */
    @Test
    @DisplayName("two correct build")
    void twoCorrectBuild(){
        //first turn - first build = 2,2
        hephaestus.selectMoves(gameBoard); //restart oldPosition
        assertTrue(hephaestus.isBuildable(build),"1");
        assertTrue(hephaestus.build(build),"2"); //build = 2,2

        //first turn - second build = 2,2 again
        assertFalse(hephaestus.getPhase(4).isMust(),"3");

        hephaestus.getBuildableSpaces(gameBoard);
        assertTrue(hephaestus.getPhase(5).isMust(),"4"); //second build is now a must
        assertTrue(hephaestus.isBuildable(build),"4a");
        assertTrue(hephaestus.build(build),"4b"); //build = 2,2

        assertFalse(hephaestus.getPhase(5).isMust(),"5");

        //second turn
        hephaestus.selectMoves(gameBoard); //restart oldPosition;
        assertTrue(hephaestus.getPhase(2).isMust(),"5b");
        assertTrue(hephaestus.getPhase(3).isMust(),"6");

        build = gameBoard.getSpace(1,2);
        assertTrue(hephaestus.isBuildable(build),"7");
        assertTrue(hephaestus.build(build),"8"); //now build in 1,2

        assertFalse(hephaestus.getPhase(4).isMust(),"9a");
        assertFalse(hephaestus.getPhase(5).isMust(),"9b");
    }


    /**
     * test getPhase.isMust parameter during the succession of one move and two build actions in a single turn
     * the second build is wrong: try to build on different spaces
     */
    @Test
    @DisplayName("try to build on another space")
    void twoWrongBuild(){
        //first turn - first build = 2,2
        hephaestus.selectMoves(gameBoard); //restart oldPosition
        assertTrue(hephaestus.isBuildable(build),"1");
        assertTrue(hephaestus.build(build),"2");

        //first turn - second build
        hephaestus.getBuildableSpaces(gameBoard); //select second build
        assertTrue(hephaestus.getPhase(5).isMust(),"4"); //second build is now a must

        build = gameBoard.getSpace(1,2);
        assertFalse(hephaestus.isBuildable(build),"4a"); //try to build in 1,2
        assertFalse(hephaestus.build(build),"4b");

        assertTrue(hephaestus.getPhase(5).isMust(),"5"); //second build is still a must
        build = gameBoard.getSpace(2,2); //select build = 2,2 again

        assertTrue(hephaestus.isBuildable(build),"6");
        assertTrue(hephaestus.build(build),"7");
    }
}