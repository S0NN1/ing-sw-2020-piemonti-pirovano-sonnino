package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerForTest;
import it.polimi.ingsw.model.player.gods.advancedgods.Zeus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class ZeusTest ...
 *
 * @author Alice Piemonti
 * Created on 22/05/2020
 */
public class ZeusTest {

    private Worker zeus;
    private GameBoard gameBoard;

    @BeforeEach
    void init() {
        zeus = new Zeus(PlayerColors.RED);
        gameBoard = new GameBoard();
    }

    /**
     * Method canBuildOnto test the correct behavior of Zeus's power: to build under itself
     */
    @Test
    @DisplayName("build under itself")
    void canBuildOntoTest() {
        Space space = gameBoard.getSpace(3,2);
        zeus.setPosition(space);
        assertTrue(zeus.canBuildOnto(space),"1"); //lv0 to lv1

        zeus.build(space );
        zeus.build(space);
        zeus.build(space);

        assertFalse(zeus.canBuildOnto(space), "2"); //lv3 to dome
    }

    /**
     * Method normalBuildTest test if Zeus can build a dome on a neighbour space
     */
    @Test
    @DisplayName("build in neighbour space")
    void normalBuildTest() {
        Space position = gameBoard.getSpace(3,2);
        Space build = gameBoard.getSpace(3,3);
        zeus.setPosition(position);

        assertTrue(zeus.canBuildOnto(build),"1"); //build on a neighbour space

        zeus.build(build);
        zeus.build(build);
        zeus.build(build);

        assertTrue(zeus.canBuildOnto(build),"2"); //build a dome on a neighbour space

        zeus.build(build);
        assertFalse(zeus.canBuildOnto(build),"2b"); //build on a completed tower

        build = gameBoard.getSpace(4,4);
        assertFalse(zeus.canBuildOnto(build),"3");    //build on a not neighbour space

        build = gameBoard.getSpace(3,1);
        build.setWorker(new WorkerForTest(PlayerColors.GREEN));
        assertFalse(zeus.canBuildOnto(build),"4"); //build on a not empty space
    }
}
