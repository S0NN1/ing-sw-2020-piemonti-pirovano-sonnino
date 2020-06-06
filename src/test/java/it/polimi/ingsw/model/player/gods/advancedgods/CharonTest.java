package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.simplegods.Apollo;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.MoveMessage;
import it.polimi.ingsw.server.answers.worker.SelectSpacesMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class CharonTest tests Charon class
 *
 * @author Alice Piemonti
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
            Worker worker = new Apollo(PlayerColors.RED);
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
            Worker worker = new Apollo(PlayerColors.RED);
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
            Worker worker = new Apollo(PlayerColors.RED);
            worker.setPosition(space5);
            Worker worker2 = new Apollo(PlayerColors.BLUE);
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
            Worker worker = new Apollo(PlayerColors.RED);
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
            Worker worker = new Apollo(PlayerColors.GREEN);
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
        VirtualClientStub virtualClient = new VirtualClientStub();
        charon.createListeners(virtualClient);
        position = gameBoard.getSpace(3,2);
        charon.setPosition(position);
        Space space = gameBoard.getSpace(3,1);
        Worker worker = new Apollo(PlayerColors.BLUE);
        worker.setPosition(space);

        assertTrue(charon.forceWorker(space,gameBoard),"1");
        assertNull(space.getWorker(),"2");
        assertEquals(gameBoard.getSpace(3,3).getWorker(), worker,"3");
        assertEquals(gameBoard.getSpace(3,3), worker.getPosition(), "4");

        //check virtual client received values
        assertNotNull(virtualClient.getMove(),"5");
        assertEquals(3, virtualClient.getMove().getOldPosition().getRow(),"6");
        assertEquals(1, virtualClient.getMove().getOldPosition().getColumn(),"7");
        assertEquals(3, virtualClient.getMove().getNewPosition().getRow(),"8");
        assertEquals(3, virtualClient.getMove().getNewPosition().getColumn(),"9");
    }

    /**
     * Method notifyWithForceWorkerSpacesTest tests notifyWithForceWorkerSpaces method
     */
    @Test
    @DisplayName("notify with force spaces")
    void notifyWithForceWorkerSpacesTest() {
        VirtualClientStub virtualClient = new VirtualClientStub();
        charon.createListeners(virtualClient);
        position = gameBoard.getSpace(3,3);
        charon.setPosition(position);
        Space space = gameBoard.getSpace(3,4);
        Space space2 = gameBoard.getSpace(4,3);
        Worker worker1 = new Apollo(PlayerColors.BLUE);
        worker1.setPosition(space);
        Worker worker2 = new Apollo((PlayerColors.GREEN));
        worker2.setPosition(space2);
        charon.notifyWithForceWorkerSpaces(gameBoard);

        assertNotNull(virtualClient.getSelectMoves(),"1");
        assertEquals(1, virtualClient.getSelectMoves().size(),"2");
    }


    /**
     * this class receives messages from different listeners
     */
    private static class VirtualClientStub extends VirtualClient {

        private ArrayList<Couple> selectMoves;
        private Move move;

        /**
         * save the message received in an appropriate field
         */
        @Override
        public void send(Answer serverAnswer) {
            if (serverAnswer instanceof SelectSpacesMessage) {
                selectMoves = ((SelectSpacesMessage) serverAnswer).getMessage();
            } else fail("unknown message");
        }

        /**
         * Send the message to all playing clients, thanks to the GameHandler sendAll method.
         *
         * @param serverAnswer the message to be sent.
         */
        @Override
        public void sendAll(Answer serverAnswer) {
            if (serverAnswer instanceof MoveMessage) {
                move = ((MoveMessage) serverAnswer).getMessage();
            } else fail("unknown message");
        }

        public ArrayList<Couple> getSelectMoves() {
            return selectMoves;
        }

        public Move getMove() {
            return move;
        }
    }
}

