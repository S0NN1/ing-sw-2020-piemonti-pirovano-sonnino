package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.GameHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class AthenaTest {

    //TODO complete Athena and AthenaTest
    @Test
    @DisplayName("test move up")
    void moveUpTest() throws OutOfBoundException {

        GameBoard gameBoard = new GameBoard();
        TurnControllerStub turnController = new TurnControllerStub(null,null,null);
        Worker athena = new Athena(PlayerColors.BLUE, turnController);

        Space firstPosition = gameBoard.getSpace(1,1);
        Space secondPosition = gameBoard.getSpace(1,2);
        Space thirdPosition = gameBoard.getSpace(1,3);
        athena.setPosition(firstPosition);
        secondPosition.getTower().addLevel();

        assertTrue(athena.isSelectable(secondPosition));
        turnController.setEventNull();
        athena.move(secondPosition);
        assertEquals("Athena",turnController.getEvent());

        assertTrue(athena.isSelectable(thirdPosition));
        turnController.setEventNull();
        athena.move(thirdPosition);
        assertNull(turnController.getEvent());

    }

    private class TurnControllerStub extends TurnController{

        private String event;

        public void setEventNull(){
            event = null;
        }

        public String getEvent() {
            return event;
        }

        public TurnControllerStub(Controller controller, ActionController actionController, GameHandler gameHandler) {
            super(controller, actionController, gameHandler);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if(evt.getNewValue().equals("AthenaMovedUp") ){
                event = "Athena";
            }
            else {
                super.propertyChange(evt);
            }
        }

    }
}