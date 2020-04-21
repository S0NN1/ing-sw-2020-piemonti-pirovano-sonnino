package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemeterTest {

    Worker demeter;
    GameBoard gameBoard;
    Space build;

    @BeforeEach
    void init(){
        demeter = new Demeter(PlayerColors.GREEN);
        gameBoard = new GameBoard();
        demeter.setPosition(gameBoard.getSpace(1,1));
        build = gameBoard.getSpace(2,2);
    }

    @Test
    @DisplayName("one move one build")
    void oneNormalBuild(){
        //first turn
        demeter.notifyWithMoves(gameBoard);
        assertTrue(demeter.getPhase(2).isMust(),"1"); //1,1

        demeter.getBuildableSpaces(gameBoard); //select build
        assertTrue(demeter.getPhase(3).isMust(),"2"); //first build is a must

        assertTrue(demeter.isBuildable(build),"3"); //build = 2,2
        assertTrue(demeter.build(build),"4");

        assertFalse(demeter.getPhase(4).isMust(),"4a");
        assertFalse(demeter.getPhase(5).isMust(),"4b");

        //second turn
        demeter.notifyWithMoves(gameBoard); //restart oldPosition value
        assertTrue(demeter.getPhase(2).isMust(),"5");
        demeter.getBuildableSpaces(gameBoard);
        assertTrue(demeter.getPhase(3).isMust(),"6");

        build = gameBoard.getSpace(2,1);
        assertTrue(demeter.isBuildable(build),"7"); //try to build on another space = 2,1
        assertTrue(demeter.build(build),"8");

        assertFalse(demeter.getPhase(4).isMust(),"9a");
        assertFalse(demeter.getPhase(5).isMust(),"9b");
    }

    @Test
    @DisplayName("one move one build but build on the same space")
    void oneBuildInTheSame(){
        //first turn - build = 2,2
        demeter.getBuildableSpaces(gameBoard); //select a build

        assertTrue(demeter.isBuildable(build),"1");
        assertTrue(demeter.build(build),"2");

        //second turn - build in 2,2 again
        demeter.notifyWithMoves(gameBoard); //restart oldPosition
        assertTrue(demeter.getBuildableSpaces(gameBoard).contains(build),"3");

        assertTrue(demeter.isBuildable(build),"4");
        assertTrue(demeter.build(build),"5");

    }

    @Test
    @DisplayName("two correct build")
    void twoCorrectBuild(){
        //first turn - first build
        demeter.selectMoves(gameBoard); //restart oldPosition
        assertTrue(demeter.isBuildable(build),"1");
        assertTrue(demeter.build(build),"2"); //build = 2,2

        //first turn - second build
        assertFalse(demeter.getPhase(4).isMust(),"3");
        build = gameBoard.getSpace(1,2);

        demeter.getBuildableSpaces(gameBoard);
        assertTrue(demeter.getPhase(5).isMust(),"4"); //second build is now a must
        assertTrue(demeter.isBuildable(build),"4a");
        assertTrue(demeter.build(build),"4b"); //build = 1,2

        assertFalse(demeter.getPhase(5).isMust(),"5");

        //second turn
        demeter.selectMoves(gameBoard); //restart oldPosition;
        assertTrue(demeter.getPhase(2).isMust(),"5b");
        assertTrue(demeter.getPhase(3).isMust(),"6");

        assertTrue(demeter.isBuildable(build),"7");
        assertTrue(demeter.build(build),"8"); //build in 1,2 again

        assertFalse(demeter.getPhase(4).isMust(),"9a");
        assertFalse(demeter.getPhase(5).isMust(),"9b");
    }

    @Test
    @DisplayName("try to build on the same space")
    void twoWrongBuild(){
        //first turn - first build = 2,2
        demeter.selectMoves(gameBoard); //restart oldPosition
        assertTrue(demeter.isBuildable(build),"1");
        assertTrue(demeter.build(build),"2");

        //first turn - second build
        demeter.getBuildableSpaces(gameBoard); //select second build
        assertTrue(demeter.getPhase(5).isMust(),"4"); //second build is now a must

        assertFalse(demeter.isBuildable(build),"4a"); //try to build again in 2,2
        assertFalse(demeter.build(build),"4b");

        assertTrue(demeter.getPhase(5).isMust(),"5"); //second build is still a must
        build = gameBoard.getSpace(1,2); //select another build

        assertTrue(demeter.isBuildable(build),"6");
        assertTrue(demeter.build(build),"7");
    }
}