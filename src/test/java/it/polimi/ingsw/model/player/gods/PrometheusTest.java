package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusTest {

    Worker prometheus;
    GameBoard gameBoard;

    @BeforeEach
    void init(){
        prometheus = new Prometheus(PlayerColors.RED);
        gameBoard = new GameBoard();
        prometheus.setPosition(gameBoard.getSpace(1,1));
    }

    @Test
    @DisplayName("one move one build")
    void moveBuild(){
        //first turn
        assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(),"1");
        assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(),"2");

        prometheus.notifyWithMoves(gameBoard);
        Space space = gameBoard.getSpace(2,2);
        assertTrue(prometheus.isSelectable(space),"3");
        assertTrue(prometheus.move(space),"4"); //move from 1,1 to 2,2

        prometheus.notifyWithBuildable(gameBoard);
        space = gameBoard.getSpace(1,1);
        assertTrue(prometheus.isBuildable(space),"5");
        assertTrue(prometheus.build(space),"6");    //build in 1,1

        //second turn
        assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(),"7");
        assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(),"8");

        prometheus.notifyWithMoves(gameBoard);
        space = gameBoard.getSpace(1,2);
        assertTrue(prometheus.isSelectable(space),"9");
        assertTrue(prometheus.move(space),"10"); //move from 2,2 to 1,2

        prometheus.notifyWithBuildable(gameBoard);
        space = gameBoard.getSpace(1,1);
        assertTrue(prometheus.isBuildable(space),"11");
        assertTrue(prometheus.build(space),"12");    //build in 1,1

    }

    @Test
    @DisplayName("one move up one build")
    void moveUpBuild() throws OutOfBoundException {
        //first turn
        assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(),"1");
        assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(),"2");

        prometheus.notifyWithMoves(gameBoard);
        Space space = gameBoard.getSpace(2,2);
        space.getTower().addLevel();
        assertTrue(prometheus.isSelectable(space),"3");
        assertTrue(prometheus.move(space),"4"); //move up from 1,1 to 2,2
        assertEquals(1,prometheus.getPosition().getTower().getHeight());

        prometheus.notifyWithBuildable(gameBoard);
        space = gameBoard.getSpace(1,1);
        assertTrue(prometheus.isBuildable(space),"5");
        assertTrue(prometheus.build(space),"6");    //build in 1,1

        //second turn
        assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(),"7");
        assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(),"8");

        prometheus.notifyWithMoves(gameBoard);
        space = gameBoard.getSpace(1,2);
        space.getTower().addLevel();
        space.getTower().addLevel();
        assertTrue(prometheus.isSelectable(space),"9");
        assertTrue(prometheus.move(space),"10"); //move up from 2,2 to 1,2
        assertEquals(2,prometheus.getPosition().getTower().getHeight());

        prometheus.notifyWithBuildable(gameBoard);
        space = gameBoard.getSpace(1,1);
        assertTrue(prometheus.isBuildable(space),"11");
        assertTrue(prometheus.build(space),"12");    //build in 1,1

    }
}