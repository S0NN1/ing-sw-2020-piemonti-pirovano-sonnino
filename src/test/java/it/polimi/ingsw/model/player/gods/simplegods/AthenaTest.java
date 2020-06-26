package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.MoveMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AthenaTest class tests Athena class.
 *
 * @author Alice Piemonti
 */
class AthenaTest {

  /**
   * Method moveUpTest tests move up effect of Athena.
   *
   * @throws OutOfBoundException when position is invalid.
   */
  @Test
  @DisplayName("test move up")
  void moveUpTest() throws OutOfBoundException {

    GameBoard gameBoard = new GameBoard();
    GameHandler gameHandler = new GameHandler(null);
    TurnControllerStub turnController =
        new TurnControllerStub(
            new Controller(new Game(), gameHandler), gameHandler, new ActionController(gameBoard));
    VirtualClientStub client = new VirtualClientStub();
    Worker athena = new Athena(PlayerColors.BLUE, turnController);
    athena.createListeners(client);

    Space firstPosition = gameBoard.getSpace(1, 1);
    Space secondPosition = gameBoard.getSpace(1, 2);
    Space thirdPosition = gameBoard.getSpace(1, 3);
    athena.setPosition(firstPosition); // first position lv0
    secondPosition.getTower().addLevel(); // second position lv1

    assertTrue(athena.isSelectable(secondPosition), "1");
    turnController.setEventNull(); // restart TurnController
    athena.move(secondPosition);

    // check if moveUpListener has been notified correctly with AthenaMovedUp
    assertEquals("Athena", turnController.getEvent(), "2");

    // check if moveListener has been notified correctly
    assertEquals(secondPosition.getRow(), client.move.getNewPosition().getRow(), "3");
    assertEquals(secondPosition.getColumn(), client.move.getNewPosition().getColumn(), "4");
    assertEquals(firstPosition.getRow(), client.move.getOldPosition().getRow(), "5");
    assertEquals(firstPosition.getColumn(), client.move.getOldPosition().getColumn(), "6");

    assertTrue(athena.isSelectable(thirdPosition), "7"); // third position lv0
    turnController.setEventNull(); // restart TurnController
    athena.move(thirdPosition);

    // check if moveUpListener has been notified with AthenaNormalMove
    assertEquals("normal", turnController.getEvent(), "8");

    // check if moveListener has been notified correctly
    assertEquals(thirdPosition.getRow(), client.move.getNewPosition().getRow(), "9");
    assertEquals(thirdPosition.getColumn(), client.move.getNewPosition().getColumn(), "10");
    assertEquals(secondPosition.getRow(), client.move.getOldPosition().getRow(), "11");
    assertEquals(secondPosition.getColumn(), client.move.getOldPosition().getColumn(), "12");
  }

  /** Class VirtualClientStub defines a stub for VirtualClient class. */
  private static class VirtualClientStub extends VirtualClient {
    Move move;

    /**
     * Method send prepares the answer for sending it through the network, putting it in a
     * serialized package, called SerializedMessage, then sends the packaged answer to the
     * transmission protocol, located in the socket-client handler.
     *
     * @see SocketClientConnection for more details.
     * @param serverAnswer of type Answer - the answer to be sent to the user.
     * @see VirtualClient#send(Answer)
     */
    @Override
    public void send(Answer serverAnswer) {
      if (serverAnswer instanceof MoveMessage) {
        move = ((MoveMessage) serverAnswer).getMessage();
      } else fail("wrong type message");
    }
    /**
     * Method sendAll sends the message to all playing clients, thanks to the GameHandler sendAll
     * method. It's triggered from the model's listeners after a player action.
     *
     * @param serverAnswer of type Answer - the message to be sent.
     * @see VirtualClient#sendAll(Answer)
     */
    @Override
    public void sendAll(Answer serverAnswer) {
      if (serverAnswer instanceof MoveMessage) {
        move = ((MoveMessage) serverAnswer).getMessage();
      } else fail("wrong type message");
    }
  }

  /** Class TurnControllerStub defines a stub for TurnController class. */
  private static class TurnControllerStub extends TurnController {

    private String event;

    /**
     * Constructor TurnController creates a new TurnController instance.
     *
     * @param controller of type Controller - main controller reference.
     * @param gameHandler of type GameHandler - GameHandler reference
     * @param actionController of type ActionController - ActionController reference.
     */
    public TurnControllerStub(
        Controller controller, GameHandler gameHandler, ActionController actionController) {
      super(controller, gameHandler, actionController);
    }

    /** Method setEventNull sets event to null. */
    public void setEventNull() {
      event = null;
    }

    /**
     * Method getEvent returns the event of this TurnControllerStub object.
     *
     * @return the event (type String) of this TurnControllerStub object.
     */
    public String getEvent() {
      return event;
    }

    /**
     * Method propertyChange receives a property changed from a PropertyChangeSupport and pass
     * different arguments to the ActionController.
     *
     * @param evt of type PropertyChangeEvent - the property change event.
     * @see TurnController#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

      if (evt.getNewValue().equals("AthenaMovedUp")) {
        event = "Athena";
      } else if (evt.getNewValue().equals("AthenaNormalMove")) {
        event = "normal";
      } else fail("no string");
    }
  }
}
