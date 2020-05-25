package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class CharonTest tests Charon class
 *
 * @author Alice Piemonti
 * Created on 25/05/2020
 */
public class CharonTest {

    Charon charon;
    GameBoard gameBoard;
    Space position;

    @BeforeEach
    void init() {
        charon = new Charon(PlayerColors.GREEN);
        gameBoard = new GameBoard();
    }

    @Nested
    @DisplayName("force worker")
    class forceWorkerTests {

        @Test
        @DisplayName("out of grid")
        void outOfGrid() {
            position = gameBoard.getSpace(4,3);
            Space space1 = gameBoard.getSpace(3,3);     //out of grid space
            space1.setWorker(new WorkerForTest(PlayerColors.RED));
            assertFalse(charon.forceWorker(space1, gameBoard),"1");
        }

        @Test
        @DisplayName("not reachable space")
        void notReachable() {
            position = gameBoard.getSpace(4,3);
            Space space2 = gameBoard.getSpace(1,1);     //not reachable space
            space2.setWorker(new WorkerForTest(PlayerColors.RED));
            assertFalse(charon.forceWorker(space2, gameBoard),"2");
        }

        @Test
        @DisplayName("empty space")
        void emptySpace() {
            position = gameBoard.getSpace(4,3);
            Space space3 = gameBoard.getSpace(4,3);     //empty space
            assertFalse(charon.forceWorker(space3, gameBoard),"3");
        }

        @Test
        @DisplayName("space = position")
        void samePosition() {
            position = gameBoard.getSpace(4,3);
            Space space4 = gameBoard.getSpace(4,2);     //space = position
            assertFalse(charon.forceWorker(space4, gameBoard),"4");
        }

        @Test
        @DisplayName("force on another worker")
        void occupiedSpace() {
            position = gameBoard.getSpace(4,3);
            Space space5 = gameBoard.getSpace(4,2);
            space5.setWorker(new WorkerForTest(PlayerColors.RED));
            gameBoard.getSpace(4,4).setWorker(new WorkerForTest(PlayerColors.BLUE)); //occupied space
            assertFalse(charon.forceWorker(space5, gameBoard),"5");
        }

        @Test
        @DisplayName("force on a complete tower")
        void completeSpace() {
            position = gameBoard.getSpace(4,3);
            Space space6 = gameBoard.getSpace(4,2);
            space6.setWorker(new WorkerForTest(PlayerColors.RED));
            gameBoard.getSpace(4,4).getTower().setDome(true); //complete tower
            assertFalse(charon.forceWorker(space6, gameBoard),"6");
        }
    }

    /**
     * Method forceWorkerTest tests forceWorker method
     */
    @Test
    @DisplayName("force worker correctly")
    void forceWorkerTest() {
        position = gameBoard.getSpace(3,2);
        Space space = gameBoard.getSpace(3,1);
        Worker worker = new WorkerForTest(PlayerColors.BLUE);
        space.setWorker(worker);

        assertTrue(charon.forceWorker(space,gameBoard),"1");
        assertNull(space.getWorker(),"2");
        assertEquals(gameBoard.getSpace(3,3).getWorker(), worker,"3");
        assertEquals(gameBoard.getSpace(3,3), worker.getPosition(), "4");
    }
}

