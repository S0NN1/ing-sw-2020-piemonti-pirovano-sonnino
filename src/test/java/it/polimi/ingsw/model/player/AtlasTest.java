package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.Space;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author alice
 */
class AtlasTest {

    @Test
    void buildDomeTest() throws OutOfBoundException {
        Worker atlas = new Atlas(PlayerColors.RED);
        Space space = new Space();
        space.getTower().addLevel();



    }
}