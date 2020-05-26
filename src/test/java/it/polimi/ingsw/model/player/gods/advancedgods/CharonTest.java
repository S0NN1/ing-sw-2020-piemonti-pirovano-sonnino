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

    /**
     * Method init initialize Charon worker and gameBoard
     */
    @BeforeEach
    void init() {
        charon = new Charon(PlayerColors.GREEN);
        gameBoard = new GameBoard();
        position = gameBoard.getSpace(4,3);
        charon.setPosition(position);
    }

    /**
     * Class forceWorkerTests tests forceWorker method
     *
     * @author Alice Piemonti
     * Created on 26/05/2020
     */
    @Nested
    @DisplayName("force worker")
    class forceWorkerTests {

        /**
         * Method outOfGrid try to force a worker out of the grid
         */
        @Test
        @DisplayName("out of grid")
        void outOfGrid() {
            Space space1 = gameBoard.getSpace(3,3);     //out of grid space
            Worker worker = new WorkerForTest(PlayerColors.RED);
            worker.setPosition(space1);
            assertFalse(charon.forceWorker(space1, gameBoard),"1");
        }

        /**
         * Method notReachable try to force a worker from a not reachable space
         */
        @Test
        @DisplayName("not reachable space")
        void notReachable() {
            Space space2 = gameBoard.getSpace(1,1);     //not reachable space
            Worker worker = new WorkerForTest(PlayerColors.RED);
            worker.setPosition(space2);
            assertFalse(charon.forceWorker(space2, gameBoard),"2");
        }

        /**
         * Method emptySpace try to force from an empty space
         */
        @Test
        @DisplayName("empty space")
        void emptySpace() {
            Space space3 = gameBoard.getSpace(4,2);     //empty space
            assertFalse(charon.forceWorker(space3, gameBoard),"3");
        }

        /**
         * Method samePosition try to force Charon
         */
        @Test
        @DisplayName("space = position")
        void samePosition() {
            Space space4 = gameBoard.getSpace(4,3);     //space = position
            assertFalse(charon.forceWorker(space4, gameBoard),"4");
        }

        /**
         * Method occupiedSpace try to force a worker to an already occupied space
         */
        @Test
        @DisplayName("force on another worker")
        void occupiedSpace() {
            Space space5 = gameBoard.getSpace(4,2);
            Worker worker = new WorkerForTest(PlayerColors.RED);
            worker.setPosition(space5);
            Worker worker2 = new WorkerForTest(PlayerColors.BLUE);
            worker2.setPosition(gameBoard.getSpace(4,4)); //occupied space
            assertFalse(charon.forceWorker(space5, gameBoard),"5");
        }

        /**
         * Method completeSpace try to force a worker on a complete tower
         */
        @Test
        @DisplayName("force on a complete tower")
        void completeSpace() {
            Space space6 = gameBoard.getSpace(4,2);
            Worker worker = new WorkerForTest(PlayerColors.RED);
            worker.setPosition(space6);
            gameBoard.getSpace(4,4).getTower().setDome(true); //complete tower
            assertFalse(charon.forceWorker(space6, gameBoard),"6");
        }

        /**
         * Method sameColor try to force a worker with the same color of Charon
         */
        @Test
        @DisplayName("force same color")
        void sameColor() {
            position = gameBoard.getSpace(3,2);
            charon.setPosition(position);
            Space space = gameBoard.getSpace(3,1);
            Worker worker = new WorkerForTest(PlayerColors.GREEN);
            worker.setPosition(space);

            assertFalse(charon.forceWorker(space,gameBoard),"1");
        }
    }

    /**
     * Method forceWorkerTest tests forceWorker method
     */
    @Test
    @DisplayName("force worker correctly")
    void forceWorkerTest() {
        position = gameBoard.getSpace(3,2);
        charon.setPosition(position);
        Space space = gameBoard.getSpace(3,1);
        Worker worker = new WorkerForTest(PlayerColors.BLUE);
        worker.setPosition(space);

        assertTrue(charon.forceWorker(space,gameBoard),"1");
        assertNull(space.getWorker(),"2");
        assertEquals(gameBoard.getSpace(3,3).getWorker(), worker,"3");
        assertEquals(gameBoard.getSpace(3,3), worker.getPosition(), "4");
    }
}

