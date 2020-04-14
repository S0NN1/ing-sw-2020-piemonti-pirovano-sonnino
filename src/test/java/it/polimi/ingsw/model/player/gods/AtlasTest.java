package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.Atlas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class AtlasTest {

    Worker atlas;
    GameBoard gameBoard;
    Space build;

    @BeforeEach
    void init(){
        atlas = new Atlas(PlayerColors.RED);
        gameBoard = new GameBoard();
        atlas.setPosition(gameBoard.getSpace(1,1));
        build = gameBoard.getSpace(2,2);

    }

    @Test
    @DisplayName("build block")
    void buildBlock() {
        assertEquals(8, atlas.getBuildableSpaces(gameBoard).size(),"1");

        assertTrue(atlas.isBuildable(build),"1b");
        assertTrue(atlas.build(build),"2"); //build a normal block
        assertEquals(1,build.getTower().getHeight(),"3");
        assertFalse(build.getTower().isCompleted(),"4");
    }

    @Test
    @DisplayName("build dome")
    void buildDome(){
        assertTrue(atlas.build(build, true),"1");

        assertTrue(build.getTower().isCompleted(),"2");
        assertEquals(0,build.getTower().getHeight(),"3");
    }
}