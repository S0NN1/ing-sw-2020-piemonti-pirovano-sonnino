package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class HestiaTest tests Hestia class
 *
 * @author Alice Piemonti
 * Created on 25/05/2020
 */
public class HestiaTest {

    Worker hestia;
    GameBoard gameBoard;
    Space build;

    /**
     * Method init initializes worker with a Hestia instance, a new gameBoard and Hestia's starting position
     */
    @BeforeEach
    void init() {
        hestia = new Hestia(PlayerColors.RED);
        gameBoard = new GameBoard();
        hestia.setPosition(gameBoard.getSpace(1,1));
        build = gameBoard.getSpace(2,2);
    }

    /**
     * test getPhase.isMust parameter during the succession of one move and one build action (normal build)
     */
    @Test
    @DisplayName("one move one build")
    void oneNormalBuild(){
        //first turn
        hestia.notifyWithMoves(gameBoard);
        assertTrue(hestia.getPhase(2).isMust(),"1"); //1,1

        hestia.getBuildableSpaces(gameBoard); //select build
        assertTrue(hestia.getPhase(3).isMust(),"2"); //first build is a must

        assertTrue(hestia.canBuildOnto(build),"3"); //build = 2,2
        assertTrue(hestia.build(build),"4");

        assertFalse(hestia.getPhase(4).isMust(),"4a");
        assertFalse(hestia.getPhase(5).isMust(),"4b");

        //second turn
        hestia.notifyWithMoves(gameBoard); //restart alreadyBuilt value
        assertTrue(hestia.getPhase(2).isMust(),"5");
        hestia.getBuildableSpaces(gameBoard);
        assertTrue(hestia.getPhase(3).isMust(),"6");

        build = gameBoard.getSpace(2,1);
        assertTrue(hestia.canBuildOnto(build),"7"); //try to build on another space = 2,1
        assertTrue(hestia.build(build),"8");

        assertFalse(hestia.getPhase(4).isMust(),"9a");
        assertFalse(hestia.getPhase(5).isMust(),"9b");
    }

    /**
     * test getPhase.isMust parameter during the succession of one move and one build action (normal build on a perimeter space)
     */
    @Test
    @DisplayName("one move one build and build on a perimeter space")
    void oneBuildOnPerimeter(){
        //first turn - build = 2,2
        hestia.getBuildableSpaces(gameBoard); //select a build

        assertTrue(hestia.canBuildOnto(build),"1");
        assertTrue(hestia.build(build),"2");

        //second turn - build in 2,0
        build = gameBoard.getSpace(2,0);
        hestia.notifyWithMoves(gameBoard); //restart alreadyBuilt
        assertTrue(hestia.getBuildableSpaces(gameBoard).contains(build),"3");

        assertTrue(hestia.canBuildOnto(build),"4");
        assertTrue(hestia.build(build),"5");
    }

    /**
     * test getPhase.isMust parameter during the succession of one move and two build actions in a single turn
     * the second build is correct: build on a not perimeter space
     */
    @Test
    @DisplayName("two correct build")
    void twoCorrectBuild(){
        //first turn - first build
        hestia.notifyWithMoves(gameBoard); //restart alreadyBuilt
        assertTrue(hestia.canBuildOnto(build),"1");
        assertTrue(hestia.build(build),"2"); //build = 2,2

        //first turn - second build
        assertFalse(hestia.getPhase(4).isMust(),"3");
        build = gameBoard.getSpace(1,2);

        hestia.getBuildableSpaces(gameBoard);
        assertTrue(hestia.getPhase(5).isMust(),"4"); //second build is now a must
        assertTrue(hestia.canBuildOnto(build),"4a");
        assertTrue(hestia.build(build),"4b"); //build = 1,2

        assertFalse(hestia.getPhase(5).isMust(),"5");

        //second turn
        hestia.notifyWithMoves(gameBoard); //restart alreadyBuilt;
        assertTrue(hestia.getPhase(2).isMust(),"5b");
        assertTrue(hestia.getPhase(3).isMust(),"6");

        build = gameBoard.getSpace(0,2);
        assertTrue(hestia.canBuildOnto(build),"7");
        assertTrue(hestia.build(build),"8"); //build in 0,2 perimeter space

        assertFalse(hestia.getPhase(4).isMust(),"9a");
        assertFalse(hestia.getPhase(5).isMust(),"9b");
    }

    /**
     * test getPhase.isMust parameter during the succession of one move and two build actions in a single turn
     * the second build is wrong: try to build on a perimeter space
     */
    @Test
    @DisplayName("try to build on a perimeter space")
    void twoWrongBuild(){
        //first turn - first build = 2,2
        hestia.notifyWithMoves(gameBoard); //restart alreadyBuilt
        assertTrue(hestia.canBuildOnto(build),"1");
        assertTrue(hestia.build(build),"2");

        //first turn - second build
        hestia.getBuildableSpaces(gameBoard); //select second build
        assertTrue(hestia.getPhase(5).isMust(),"4"); //second build is now a must

        build = gameBoard.getSpace(2,0);
        assertFalse(hestia.canBuildOnto(build),"4a"); //try to build in 2,0 perimeter space
        assertFalse(hestia.build(build),"4b");

        assertTrue(hestia.getPhase(5).isMust(),"5"); //second build is still a must
        build = gameBoard.getSpace(1,2); //select another build

        assertTrue(hestia.canBuildOnto(build),"6");
        assertTrue(hestia.build(build),"7");
    }
}
