package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerForTest;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.DoubleMoveMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class MinotaurTest {

    Worker minotaur;
    GameBoard gameBoard;
    ArrayList<Worker> workers;

    @BeforeEach
    void init(){
        minotaur = new Minotaur(PlayerColors.GREEN);
        gameBoard = new GameBoard();
        workers = new ArrayList<>();
        for(int i=0; i<8; i++){
            workers.add(new WorkerForTest(PlayerColors.RED));
        }
    }

    /**
     * test the method isSelectable (using selectMoves which calls isSelectable several times)
     * in different situations (different Minotaur's position into the gameBoard)
     */
    @Nested
    @DisplayName("isSelectable() tests with workers")
    class isSelectable{

        /**
         * Minotaur is positioned on a centered space of the gameBoard: he can move to at least 8 different spaces
         */
        @Test
        @DisplayName("normal move")
        void normalMove(){
            minotaur.setPosition(gameBoard.getSpace(1,1));
            assertEquals(8,minotaur.selectMoves(gameBoard).size(),"1");
        }

        /**
         * Minotaur is positioned on a border: he can move to at least 5 different spaces
         */
        @Test
        @DisplayName("border test")
        void testBorder(){
            minotaur.setPosition(gameBoard.getSpace(0,1));
            int k = 0;
            for(int i=0; i<2; i++){
                for(int j=0; j<3; j++){
                    if(!(i==0 && j==1)){        //initialize
                        workers.get(k).setPosition(gameBoard.getSpace(i,j));
                        k++;
                    }
                }
            }

           assertEquals(3,minotaur.selectMoves(gameBoard).size(),"2");
        }

        /**
         * Minotaur is positioned on a corner: he can move to at least 3 spaces
         */
        @Test
        @DisplayName("corner test")
        void testCorner(){
            minotaur.setPosition(gameBoard.getSpace(4,4));
            workers.get(0).setPosition(gameBoard.getSpace(3,3));
            workers.get(1).setPosition(gameBoard.getSpace(3,4));
            workers.get(2).setPosition(gameBoard.getSpace(4,3));

            assertEquals(3,minotaur.selectMoves(gameBoard).size(),"3");
        }

    }

    /**
     * test the method move in different situations (different reciprocal positions)
     */
    @Nested
    @DisplayName("move tests")
    class move {

        /**
         * test the method move when Minotaur and worker have different space's row and column
         */
        @Test
        @DisplayName("diagonal push")
        void diagonal() {
            minotaur.setPosition(gameBoard.getSpace(1, 2));
            workers.get(0).setPosition(gameBoard.getSpace(0, 1));
            workers.get(1).setPosition(gameBoard.getSpace(0, 3));
            workers.get(2).setPosition(gameBoard.getSpace(2, 1));
            workers.get(3).setPosition(gameBoard.getSpace(2, 3));

            assertEquals(6, minotaur.selectMoves(gameBoard).size());

            Space move = gameBoard.getSpace(2, 1);
            minotaur.move(move, gameBoard);  //push worker2
            assertEquals(gameBoard.getSpace(3, 0), workers.get(2).getPosition());
            assertEquals(move, minotaur.getPosition());

            minotaur.setPosition(gameBoard.getSpace(1, 2));
            move = gameBoard.getSpace(2, 3);
            minotaur.move(move, gameBoard);  //push worker3

            assertEquals(gameBoard.getSpace(3, 4), workers.get(3).getPosition());
            assertEquals(workers.get(3), gameBoard.getSpace(3, 4).getWorker());
            assertEquals(move, minotaur.getPosition());
            assertEquals(minotaur, gameBoard.getSpace(2, 3).getWorker());
        }

        /**
         * test the method move when Minotaur and worker have the same space's row
         */
        @Test
        @DisplayName("horizontal push")
        void horizontal() {
            minotaur.setPosition(gameBoard.getSpace(1, 1));
            workers.get(0).setPosition(gameBoard.getSpace(1, 0));
            workers.get(1).setPosition(gameBoard.getSpace(1, 2));

            assertEquals(7, minotaur.selectMoves(gameBoard).size(),"1");

            Space move = gameBoard.getSpace(1, 0);
            assertFalse(minotaur.isSelectable(move),"2"); //can't push worker0 out of the gameBoard

            move = gameBoard.getSpace(1, 2);
            minotaur.move(move, gameBoard); //push worker1
            assertEquals(gameBoard.getSpace(1,3), workers.get(1).getPosition(),"3");
            assertEquals(workers.get(1), gameBoard.getSpace(1, 3).getWorker(),"4");
            assertEquals(move, minotaur.getPosition(),"5");
            assertEquals(minotaur, gameBoard.getSpace(1, 2).getWorker(),"6");
        }

        /**
         * test the method move when Minotaur and worker have the same space's column
         */
        @Test
        @DisplayName("vertical push")
        void vertical() {
            minotaur.setPosition(gameBoard.getSpace(3, 4));
            workers.get(0).setPosition(gameBoard.getSpace(2, 4));
            workers.get(1).setPosition(gameBoard.getSpace(4, 4));

            assertEquals(4, minotaur.selectMoves(gameBoard).size(),"1");

            Space move = gameBoard.getSpace(4, 4);
            assertFalse(minotaur.isSelectable(move),"2"); //can't push worker1 out of the gameBoard

            move = gameBoard.getSpace(2, 4);
            minotaur.move(move, gameBoard); //push worker0
            assertEquals(gameBoard.getSpace(1, 4), workers.get(0).getPosition(),"3");
            assertEquals(workers.get(0), gameBoard.getSpace(1, 4).getWorker(),"4");
            assertEquals(move, minotaur.getPosition(),"5");
            assertEquals(minotaur, gameBoard.getSpace(2, 4).getWorker(),"6");
        }
    }

    /**
     * test the double move listener which is fired when Minotaur moves into a not empty space
     */
    @Test
    @DisplayName("double move listener test")
    void listenerTest(){
        Space oldPosition = gameBoard.getSpace(3,4);
        Space newPosition = gameBoard.getSpace(2,3);
        Space otherPosition = gameBoard.getSpace(1,2);
        minotaur.setPosition(oldPosition);
        workers.get(0).setPosition(newPosition);
        VirtualClientStub client = new VirtualClientStub();
        minotaur.createListeners(client);

        if(minotaur.isSelectable(gameBoard.getSpace(2,3))){
            minotaur.move(gameBoard.getSpace(2,3),gameBoard);

            assertEquals("MinotaurDoubleMove", client.god, "0");

            assertEquals(minotaur.getPosition().getX(),client.myMove.getNewPosition().getX(),"1");
            assertEquals(minotaur.getPosition().getY(),client.myMove.getNewPosition().getY(),"2");
            assertEquals(oldPosition.getX(),client.myMove.getOldPosition().getX(),"3");
            assertEquals(oldPosition.getY(),client.myMove.getOldPosition().getY(),"4");

            assertEquals(otherPosition.getX(),client.otherMove.getNewPosition().getX(),"5");
            assertEquals(otherPosition.getY(),client.otherMove.getNewPosition().getY(),"6");
            assertEquals(minotaur.getPosition().getX(),client.otherMove.getOldPosition().getX(),"7");
            assertEquals(minotaur.getPosition().getY(),client.otherMove.getOldPosition().getY(),"8");
        }
        else fail("space not selectable");
    }

    /**
     * this class receives messages from a DoubleMoveListener
     */
    private class VirtualClientStub extends VirtualClient {

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
    }
}