package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerForTest;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import it.polimi.ingsw.server.answers.worker.MoveMessage;
import it.polimi.ingsw.server.answers.worker.SelectSpacesMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class AresTest tests Ares class
 *
 * @author Alice Piemonti
 * Created on 26/05/2020
 */
public class AresTest {

    Ares ares1;
    Ares ares2;
    GameBoard gameBoard;
    VirtualClientStub virtualClient;

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

    @Test
    @DisplayName("wrong spaces neighbouring unmoved worker")
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

    @Test
    @DisplayName("can remove test")
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
        remove.setWorker(new WorkerForTest(PlayerColors.RED));  //space not empty
        assertFalse(ares1.canRemove(remove, unmoved),"7");

        remove = gameBoard.getSpace(1,3);   //correct space
        assertTrue(ares1.canRemove(remove, unmoved),"8");
    }


    /**
     * this class receives messages from different listeners
     */
    private static class VirtualClientStub extends VirtualClient {

        private ArrayList<Couple> selectMoves;
        private Couple remove;
        private Action action;

        /**
         * save the message received in an appropriate field
         */
        @Override
        public void send(Answer serverAnswer) {
            if (serverAnswer instanceof SelectSpacesMessage) {
                selectMoves = ((SelectSpacesMessage) serverAnswer).getMessage();
                action = ((SelectSpacesMessage) serverAnswer).getAction();
            } else fail("unknown message");
        }

        /**
         * Send the message to all playing clients, thanks to the GameHandler sendAll method.
         *
         * @param serverAnswer the message to be sent.
         */
        @Override
        public void sendAll(Answer serverAnswer) {
            if (serverAnswer instanceof BuildMessage) {
                remove = ((BuildMessage) serverAnswer).getMessage();
                action = ((BuildMessage) serverAnswer).getAction();
            } else fail("unknown message");
        }

        public ArrayList<Couple> getSelectMoves() {
            return selectMoves;
        }

        public Couple getRemove() {
            return remove;
        }

        public Action getAction() {
            return action;
        }
    }
}
