package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.ChallengerMessages;
import it.polimi.ingsw.server.answers.CustomMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class GodSelectionControllerTest tests GodSelectionController class.
 *
 * @author Luca Pirovano
 * @see GodSelectionController
 */
class GodSelectionControllerTest {

  /** Class VirtualClientStub defines a stub for VirtualClient class. */
  private static class VirtualClientStub extends VirtualClient {
    private boolean notified = false;
    private List<String> gods;
    private String message;

    /**
     * Method propertyChange states the listener property changes, forwarding the message to the
     * correct method.
     *
     * @param evt of type PropertyChangeEvent - the property change firing event.
     * @see PropertyChangeListener
     * @see VirtualClient#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      notified = true;
      if (evt.getNewValue() instanceof ChallengerMessages) {
        if (((ChallengerMessages) evt.getNewValue()).getMessage() != null)
          message = ((ChallengerMessages) evt.getNewValue()).getMessage();
        else gods = ((ChallengerMessages) evt.getNewValue()).getGodList();
      } else {
        message = ((CustomMessage) evt.getNewValue()).getMessage();
      }
    }
  }

  /** Class GameHandlerStub defines a stub for GameHandler class. */
  private static class GameHandlerStub extends GameHandler {
    public int started = 1;

    /**
     * Constructor GameHandler creates a new GameHandler instance.
     *
     * @param server of type Server - the main server class.
     */
    public GameHandlerStub(Server server) {
      super(server);
    }

    /**
     * Method getCurrentPlayerID returns the current player client ID, getting it from the
     * currentPlayer reference in the Game class.
     *
     * @return the currentPlayerID (type int) of this GameHandler object.
     * @see GameHandler#getCurrentPlayerID()
     */
    @Override
    public int getCurrentPlayerID() {
      return 0;
    }

    /**
     * Method singleSend sends a message to a client, identified by his ID number, through the
     * server socket.
     *
     * @param message of type Answer - the message to be sent to the client.
     * @param id of type int - the unique identification number of the client to be contacted.
     * @see GameHandler#singleSend(Answer, int)
     */
    @Override
    public void singleSend(Answer message, int id) {
      System.out.println("OK");
    }

    /**
     * Method sendAllExcept makes the same as the previous method, but it iterates on all the
     * clients present in the game, except the declared one.
     *
     * @param message of type Answer - the message to be transmitted.
     * @param excludedID of type int - the client which will not receive the communication.
     * @see GameHandler#sendAllExcept(Answer, int)
     */
    @Override
    public void sendAllExcept(Answer message, int excludedID) {
      System.out.println(
          Constants.ANSI_RED
              + "\nSemi-broadcast message: "
              + Constants.ANSI_RESET
              + message.getMessage());
    }

    /**
     * Method sendAll does the same as the previous method, but it iterates on all the clients
     * present in the game. It's a full effects broadcast.
     *
     * @param message of type Answer - the message to broadcast (at single match participants'
     *     level).
     * @see GameHandler#sendAll(Answer)
     */
    @Override
    public void sendAll(Answer message) {
      System.out.println(
          Constants.ANSI_RED
              + "\nFull-broadcast message: "
              + Constants.ANSI_RESET
              + message.getMessage());
    }

    /**
     * Method isStarted returns if the game has started (the started attribute becomes true after
     * the challenger selection phase).
     *
     * @return int - the current game phase.
     * @see GameHandler#isStarted()
     */
    @Override
    public int isStarted() {
      return started;
    }
  }

  /** Class GameStub defines a stub for Game class. */
  private static class GameStub extends Game {
    private final DeckStub deck;
    /** Constructor Game creates a new Game instance. */
    public GameStub() {
      this.deck = new DeckStub(this);
    }

    /**
     * Method getDeck returns the deck of this Game object.
     *
     * @return the deck (type Deck) of this Game object.
     * @see Game#getDeck()
     */
    @Override
    public Deck getDeck() {
      return deck;
    }
  }

  /** Class DeckStub defines a stub for Deck class. */
  private static class DeckStub extends Deck {
    /**
     * Constructor DeckStub creates a new DeckStub instance.
     *
     * @param game of type Game
     */
    public DeckStub(Game game) {
      super(game);
    }
    /** @see Deck#chooseCard(Card, VirtualClient) */
    @Override
    public boolean chooseCard(Card card, VirtualClient client) {
      if (!super.getCards().contains(card)) {
        return false;
      }
      super.getCards().remove(card);
      return true;
    }
  }

  final Server server = new Server();
  final GameStub game = new GameStub();
  final GameHandlerStub gameHandler = new GameHandlerStub(server);
  final Controller controller = new Controller(game, gameHandler);
  final VirtualClientStub virtualClient = new VirtualClientStub();
  final GodSelectionController selectionController =
      new GodSelectionController(new CardSelectionModel(game.getDeck()), controller, virtualClient);

  /** Method setUp initializes values. */
  @BeforeEach
  void setUp() {
    controller.getModel().createNewPlayer(new Player("Luca", 0));
    controller.getModel().createNewPlayer(new Player("Alice", 1));
  }

  /** Method selectionFlowTest tests god selection flow management. */
  @Test
  @DisplayName("God Selection flow management test, all cases")
  void selectionFlowTest() {
    controller.getModel().setCurrentPlayer(controller.getModel().getActivePlayers().get(0));
    // God list and description testing

    selectionController.propertyChange(
        new PropertyChangeEvent(this, null, null, new ChallengerPhaseAction("LIST", null)));
    assertTrue(virtualClient.notified);
    assertEquals(Card.godsName(), virtualClient.gods);
    virtualClient.notified = false;
    selectionController.propertyChange(
        new PropertyChangeEvent(this, null, null, new ChallengerPhaseAction("DESC", Card.APOLLO)));
    assertTrue(virtualClient.notified);
    assertEquals(virtualClient.message, Card.APOLLO.godsDescription());

    // God deck addition test
    assertTrue(selectionController.add(Card.APOLLO));
    assertTrue(virtualClient.message.contains("God APOLLO has been added"));
    assertFalse(selectionController.add(Card.APOLLO));

    assertTrue(selectionController.add(Card.PAN));
    assertTrue(virtualClient.message.contains("God PAN has been added"));
    assertFalse(selectionController.add(Card.PROMETHEUS));

    // God choosing test
    gameHandler.started = 2;
    controller.getModel().setCurrentPlayer(controller.getModel().getActivePlayers().get(0));
    assertFalse(selectionController.lastSelection());
    assertTrue(selectionController.choose(Card.APOLLO));
    assertFalse(selectionController.choose(Card.APOLLO));
    assertFalse(selectionController.choose(Card.ATHENA));
    assertTrue(selectionController.lastSelection());
  }
}
