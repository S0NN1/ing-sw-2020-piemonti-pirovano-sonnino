package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerForTest;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class ApolloTest {

    /**
     * test the method move when space isn't empty
     */
    @Test
    @DisplayName("Change position with another worker")
    void moveTest() {
        Worker apollo = new Apollo(PlayerColors.RED);
        Worker worker = new WorkerForTest((PlayerColors.BLUE));

        Space spaceInit = new Space();
        Space spaceFinal = new Space();
        apollo.setPosition(spaceInit);
        worker.setPosition(spaceFinal);

        assertFalse(spaceFinal.isEmpty(), "1");
        apollo.move(spaceFinal);

        assertEquals(spaceFinal, apollo.getPosition(),"2");
        assertEquals(spaceInit, worker.getPosition(),"3");
        assertEquals(apollo, spaceFinal.getWorker(),"4");
        assertEquals(worker, spaceInit.getWorker(),"5");
    }

    /**
     * test the method selectMoves which must return not empty spaces
     */
    @Test
    void getMovesTest() {
        Worker worker1 = new WorkerForTest(PlayerColors.BLUE);
        Worker worker2 = new WorkerForTest(PlayerColors.BLUE);
        Worker worker3 = new WorkerForTest(PlayerColors.RED);
        Worker apollo = new Apollo(PlayerColors.RED);

        GameBoard gameBoard = new GameBoard();
        worker1.setPosition(gameBoard.getSpace(0,1));
        worker2.setPosition(gameBoard.getSpace(0,0));
        worker3.setPosition(gameBoard.getSpace(2,1));   //selectMoves shouldn't return this space because worker3's color equals Apollo's color
        apollo.setPosition(gameBoard.getSpace(1,1));

        int expectedMoves = 7;
        assertEquals(expectedMoves, apollo.selectMoves(gameBoard).size());
    }

    /**
     * test double move listener which is fired when Apollo makes a move into a not empty space
     */
    @Test
    @DisplayName("double move listener test")
    void listenerTest(){
        Worker apollo = new Apollo(PlayerColors.GREEN);
        Worker worker = new WorkerForTest(PlayerColors.RED);
        VirtualClientStub client = new VirtualClientStub();
        GameBoard gameBoard = new GameBoard();
        apollo.setPosition(gameBoard.getSpace(3,4));
        worker.setPosition(gameBoard.getSpace(3,3));

        apollo.createListeners(client);
        apollo.move(gameBoard.getSpace(3,3));

        assertEquals("ApolloDoubleMove", client.god, "0");

        assertEquals(worker.getPosition().getRow(),client.myMove.getOldPosition().getRow(),"1");
        assertEquals(worker.getPosition().getColumn(),client.myMove.getOldPosition().getColumn(),"2");
        assertEquals(apollo.getPosition().getRow(),client.myMove.getNewPosition().getRow(),"3");
        assertEquals(apollo.getPosition().getRow(),client.myMove.getNewPosition().getColumn(),"4");

        assertEquals(apollo.getPosition().getRow(),client.otherMove.getOldPosition().getRow(),"5");
        assertEquals(apollo.getPosition().getColumn(),client.otherMove.getOldPosition().getColumn(),"6");
        assertEquals(worker.getPosition().getRow(),client.otherMove.getNewPosition().getRow(),"7");
        assertEquals(worker.getPosition().getColumn(),client.otherMove.getNewPosition().getColumn(),"8");
    }

    /**
     * this class receives messages from a DoubleMoveListener
     */
    private static class VirtualClientStub extends VirtualClient {

        String god;

        Move myMove;

        Move otherMove;
        /**
         * save the message received in an appropriate field
         */
        @Override
        public void send(Answer serverAnswer) {
            if(serverAnswer instanceof DoubleMoveMessage){
                myMove = ((DoubleMoveMessage) serverAnswer).getMyMove();
                otherMove = ((DoubleMoveMessage) serverAnswer).getOtherMove();
                god = ((DoubleMoveMessage) serverAnswer).getMessage();
            }
            else fail("not double move message");
        }

        @Override
        public void sendAll(Answer serverAnswer) {
            if(serverAnswer instanceof DoubleMoveMessage){
                myMove = ((DoubleMoveMessage) serverAnswer).getMyMove();
                otherMove = ((DoubleMoveMessage) serverAnswer).getOtherMove();
                god = ((DoubleMoveMessage) serverAnswer).getMessage();
            }
            else fail("not double move message");
        }

    }
}