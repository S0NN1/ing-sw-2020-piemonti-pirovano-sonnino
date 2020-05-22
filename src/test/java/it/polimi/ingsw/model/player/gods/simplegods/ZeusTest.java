package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
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
     * Method isReachableTest test the correct behavior of Zeus's power: to build under itself
     */
    @Test
    @DisplayName("build under itself")
    void isReachableTest() {
        Space space = gameBoard.getSpace(3,2);
        zeus.setPosition(space);
        assertTrue(zeus.canBuildOnto(space),"1"); //lv0 to lv1

        zeus.build(space, false);
        zeus.build(space, false);
        zeus.build(space,false);

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

        zeus.build(build, false);
        zeus.build(build, false);
        zeus.build(build,false);

        assertTrue(zeus.canBuildOnto(build),"2"); //build a dome on a neighbour space
    }
}
