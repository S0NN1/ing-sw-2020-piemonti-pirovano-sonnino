package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.advancedgods.Ares;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ApolloTest class tests Apollo class.
 * @author Alice Piemonti
 * @see Apollo
 */
class ApolloTest {

    /**
     * Method moveTest tests the method move when space isn't empty.
     */
    @Test
    @DisplayName("Change position with another worker")
    void moveTest() {
        Worker apollo = new Apollo(PlayerColors.RED);
        Worker worker = new Ares((PlayerColors.BLUE));

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
     * Method getMovesTest tests the method selectMoves which must return not empty spaces.
     */
    @Test
    void getMovesTest() {
        Worker worker1 = new Apollo(PlayerColors.BLUE);
        Worker worker2 = new Apollo(PlayerColors.BLUE);
        Worker worker3 = new Apollo(PlayerColors.RED);
        Worker apollo = new Apollo(PlayerColors.RED);

        GameBoard gameBoard = new GameBoard();
        worker1.setPosition(gameBoard.getSpace(0,1));
        worker2.setPosition(gameBoard.getSpace(0,0));
        worker3.setPosition(gameBoard.getSpace(2,1));   //selectMoves shouldn't return this space because
        // worker3's color equals Apollo's color
        apollo.setPosition(gameBoard.getSpace(1,1));

        int expectedMoves = 7;
        assertEquals(expectedMoves, apollo.selectMoves(gameBoard).size());
    }

    /**
     * Method listenerTest tests double move listener which is fired when Apollo makes a move into a not empty space.
     */
    @Test
    @DisplayName("double move listener test")
    void listenerTest(){
        Worker apollo = new Apollo(PlayerColors.GREEN);
        Worker worker = new Apollo(PlayerColors.RED);
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
     * Class VirtualClientStub defines a stub for VirtualClient class.
     */
    private static class VirtualClientStub extends VirtualClient {

        String god;

        Move myMove;

        Move otherMove;

        /**
         * Method send prepares the answer for sending it through the network, putting it in a serialized package, called
         * SerializedMessage, then sends the packaged answer to the transmission protocol, located in the socket-client
         * handler.
         * @see it.polimi.ingsw.server.SocketClientConnection for more details.
         * @param serverAnswer of type Answer - the answer to be sent to the user.
         * @see VirtualClient#send(Answer)
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

        /**
         * Method sendAll sends the message to all playing clients, thanks to the GameHandler sendAll method. It's triggered
         * from the model's listeners after a player action.
         *
         * @param serverAnswer of type Answer - the message to be sent.
         * @see VirtualClient#sendAll(Answer)
         */
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