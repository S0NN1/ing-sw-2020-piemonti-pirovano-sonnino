package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import it.polimi.ingsw.server.answers.worker.SelectSpacesMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class AresTest tests Ares class.
 *
 * @author Alice Piemonti
 * @see Ares
 */
public class AresTest {

    Ares ares1;
    Ares ares2;
    GameBoard gameBoard;
    VirtualClientStub virtualClient;

    /**
     * Method init creates new instances of Ares workers, a virtualClientStub, a gameBoard. Sets ares1 position and
     * all the towers from the gameBoard to level 1.
     * @throws OutOfBoundException when position is invalid.
     */
    @BeforeEach
    void init() throws OutOfBoundException {
        ares1 = new Ares(PlayerColors.BLUE);
        ares2 = new Ares(PlayerColors.BLUE);
        virtualClient = new VirtualClientStub();
        ares1.createListeners(virtualClient);
        ares2.createListeners(virtualClient);
        gameBoard = new GameBoard();
        ares1.setPosition(gameBoard.getSpace(3,4));
        for (int i=0; i< Constants.GRID_MAX_SIZE; i++) {
            for (int j=0; j< Constants.GRID_MAX_SIZE; j++) {
                gameBoard.getSpace(i,j).getTower().addLevel();
            }
        }
    }

    /**
     * Method notifyWithRemovableTest tests notifyWithRemovable method.
     */
    @Test
    @DisplayName("notify with removable method")
    void notifyWithRemovableTest() {
        Space unmoved = gameBoard.getSpace(4,4);
        Space wrongSpace = gameBoard.getSpace(3,4);
        ares2.setPosition(unmoved);
        gameBoard.getSpace(3,3).getTower().setDome(true);
        gameBoard.getSpace(4,3).getTower().setDome(true);

        assertThrows(IllegalArgumentException.class, () -> ares1.notifyWithRemovable(gameBoard,wrongSpace), "1");
        assertThrows(IllegalStateException.class, () -> ares1.notifyWithRemovable(gameBoard,unmoved),"2");

        Space unmoved1 = gameBoard.getSpace(1,2);
        ares2.setPosition(unmoved1);
        ares1.notifyWithRemovable(gameBoard,unmoved1);

        assertEquals(Action.SELECT_REMOVE, virtualClient.getAction(),"3");
        assertEquals(8, virtualClient.selectMoves.size(),"4");
        assertTrue(ares1.getPhase(5).isMust(),"5");
    }

    /**
     * Method canRemoveTest tests canRemove method.
     * @throws OutOfBoundException when add level is not possible.
     */
    @Test
    @DisplayName("can remove method")
    void canRemoveTest() throws OutOfBoundException {
        Space unmoved = gameBoard.getSpace(2,3);
        ares2.setPosition(unmoved);

        Space remove = gameBoard.getSpace(4,4); //not neighbouring
        assertFalse(ares1.canRemove(remove, unmoved),"1");

        remove = gameBoard.getSpace(3,4);   //ares1 position
        assertFalse(ares1.canRemove(remove, unmoved),"2");

        remove = gameBoard.getSpace(2,3);      //ares2 position
        assertFalse(ares1.canRemove(remove, unmoved),"3");

        remove = gameBoard.getSpace(2,2);
        remove.getTower().setDome(true);    //space with dome
        assertFalse(ares1.canRemove(remove, unmoved),"4");

        remove.getTower().setDome(false);
        remove.getTower().addLevel();
        remove.getTower().addLevel();
        remove.getTower().addLevel();       //space with completed tower
        assertFalse(ares1.canRemove(remove, unmoved),"5");

        remove = gameBoard.getSpace(3,3);
        remove.getTower().removeLevel();        //space with tower lv0
        assertFalse(ares1.canRemove(remove, unmoved),"6");

        remove = gameBoard.getSpace(1,2);
        remove.setWorker(new Ares(PlayerColors.RED));  //space not empty
        assertFalse(ares1.canRemove(remove, unmoved),"7");

        remove = gameBoard.getSpace(1,3);   //correct space
        assertTrue(ares1.canRemove(remove, unmoved),"8");
    }

    /**
     * Method removeBlockTest tests removeBlock method.
     */
    @Test
    @DisplayName("remove method")
    void removeBlockTest() {
        Space remove = gameBoard.getSpace(3,2);
        assertEquals(1, remove.getTower().getHeight(),"0"); //already lv1

        assertTrue(ares1.removeBlock(remove),"1");
        assertEquals(0, remove.getTower().getHeight(),"2");
        assertEquals(remove.getRow(), virtualClient.getRemove().getRow(),"3");
        assertEquals(remove.getColumn(), virtualClient.getRemove().getColumn(),"4");
        assertEquals(Action.REMOVE, virtualClient.getAction(),"5");

        virtualClient = new VirtualClientStub();    //reset virtualClient
        assertFalse(ares1.removeBlock(remove),"6");
        assertNull(virtualClient.getAction(),"7");
        assertNull(virtualClient.getRemove(),"8");
        assertNull(virtualClient.getSelectMoves(),"9");
    }


    /**
     * Class VirtualClientStub defines a stub for VirtualClient class.
     */
    private static class VirtualClientStub extends VirtualClient {

        private ArrayList<Couple> selectMoves;
        private Couple remove;
        private Action action;


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
            if (serverAnswer instanceof SelectSpacesMessage) {
                selectMoves = ((SelectSpacesMessage) serverAnswer).getMessage();
                action = ((SelectSpacesMessage) serverAnswer).getAction();
            } else fail("unknown message");
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
            if (serverAnswer instanceof BuildMessage) {
                remove = ((BuildMessage) serverAnswer).getMessage();
                action = ((BuildMessage) serverAnswer).getAction();
            } else fail("unknown message");
        }

        /**
         * Method getSelectMoves returns the selectMoves of this VirtualClientStub object.
         *
         *
         *
         * @return the selectMoves (type Arraylist&lt;Couple&gt;) of this VirtualClientStub object.
         */
        public ArrayList<Couple> getSelectMoves() {
            return selectMoves;
        }

        /**
         * Method getRemove returns the remove of this VirtualClientStub object.
         *
         *
         *
         * @return the remove (type Couple) of this VirtualClientStub object.
         */
        public Couple getRemove() {
            return remove;
        }

        /**
         * Method getAction returns the action of this VirtualClientStub object.
         *
         *
         *
         * @return the action (type Action) of this VirtualClientStub object.
         */
        public Action getAction() {
            return action;
        }
    }
}
