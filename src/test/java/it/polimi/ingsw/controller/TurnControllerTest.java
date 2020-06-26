package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.turnactions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnactions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workeractions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectBuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectMoveAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.advancedgods.Charon;
import it.polimi.ingsw.model.player.gods.simplegods.Apollo;
import it.polimi.ingsw.model.player.gods.simplegods.Prometheus;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.net.Socket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class TurnControllerTest tests TurnController class.
 *
 * @author Nicolò Sonnino
 * @see TurnController
 */
public class TurnControllerTest {
  final Prometheus prometheus = new Prometheus(PlayerColors.BLUE);
  final Apollo apollo = new Apollo(PlayerColors.RED);
  final Charon charon = new Charon(PlayerColors.GREEN);
  final PlayerStub piro = new PlayerStub("piro", 1);
  final PlayerStub ali = new PlayerStub("ali", 2);
  final PlayerStub sonny = new PlayerStub("sonny", 3);
  final Socket socket = new Socket();
  final ServerStub server = new ServerStub();
  final GameHandlerStub handler = new GameHandlerStub(server);
  final SocketClientConnectionStub socketClientConnectionStub =
      new SocketClientConnectionStub(socket, server);
  final VirtualClient virtualClient =
      new VirtualClient(1, "piro", socketClientConnectionStub, handler);
  final VirtualClient virtualClient2 =
      new VirtualClient(2, "ali", socketClientConnectionStub, handler);
  final VirtualClient virtualClient3 =
      new VirtualClient(3, "sonny", socketClientConnectionStub, handler);
  final HashMap<Integer, VirtualClient> idMapID =
      new HashMap<>() {
        {
          put(1, virtualClient);
          put(2, virtualClient2);
          put(3, virtualClient3);
        }
      };
  final GameBoard board = new GameBoard();
  final Game game = new Game();
  final ActionControllerStub actionControllerStub = new ActionControllerStub(board);
  final ControllerStub controllerStub = new ControllerStub(game, handler);
  final TurnController turnController =
      new TurnController(new Controller(game, handler), handler, actionControllerStub);
  PropertyChangeEvent evt1;
  PropertyChangeEvent evt2;
  PropertyChangeEvent evt3;
  PropertyChangeEvent evt4;
  PropertyChangeEvent evt5;
  PropertyChangeEvent evt6;
  PropertyChangeEvent evt7;
  PropertyChangeEvent evt8;
  PropertyChangeEvent evt9;
  PropertyChangeEvent evt10;
  PropertyChangeEvent evt11;
  PropertyChangeEvent evt12;
  PropertyChangeEvent evt13;

  /** Method init initializes values. */
  @BeforeEach
  public void init() {
    piro.getWorkers().add(prometheus);
    piro.getWorkers().add(prometheus);
    piro.getWorkers().get(0).setPhases();
    piro.getWorkers().get(1).setPhases();
    ali.getWorkers().add(apollo);
    ali.getWorkers().add(apollo);
    ali.getWorkers().get(0).setPhases();
    ali.getWorkers().get(1).setPhases();
    sonny.getWorkers().add(charon);
    sonny.getWorkers().add(charon);
    sonny.getWorkers().get(0).setPhases();
    sonny.getWorkers().get(1).setPhases();
    server.setIdMapID(idMapID);
    piro.setColor(PlayerColors.BLUE);
    ali.setColor(PlayerColors.RED);
    sonny.setColor(PlayerColors.GREEN);
    actionControllerStub.setPhase(0);
    controllerStub.getModel().getActivePlayers().add(piro);
    controllerStub.getModel().getActivePlayers().add(ali);
    controllerStub.getModel().getActivePlayers().add(sonny);
    controllerStub.getModel().setCurrentPlayer(piro);
    game.setCurrentPlayer(piro);
    evt1 = new PropertyChangeEvent(1, null, null, new StartTurnAction("start"));
    evt2 = new PropertyChangeEvent(2, null, null, new StartTurnAction("worker1"));
    evt3 = new PropertyChangeEvent(3, null, null, new StartTurnAction("worker2"));
    evt4 = new PropertyChangeEvent(4, null, null, new EndTurnAction());
    evt5 = new PropertyChangeEvent(5, null, null, new SelectBuildAction());
    evt6 = new PropertyChangeEvent(6, null, null, new SelectMoveAction(Action.SELECT_MOVE));
    evt7 = new PropertyChangeEvent(7, null, null, new MoveAction(1, 1));
    evt8 = new PropertyChangeEvent(8, null, null, new BuildAction(1, 1));
    evt9 = new PropertyChangeEvent(9, null, null, "AthenaMovedUp");
    evt10 = new PropertyChangeEvent(10, null, null, "AthenaNormalMove");
    evt11 = new PropertyChangeEvent(6, null, null, new SelectMoveAction(Action.BUILD));
    evt12 = new PropertyChangeEvent(6, null, null, new SelectBuildAction(Action.SELECT_REMOVE));
    evt13 = new PropertyChangeEvent(6, null, null, new StartTurnAction("worker1"));
  }

  /** Method endTurnActionTest tests EndTurnAction. */
  @Test
  @DisplayName("EndTurnAction test")
  public void endTurnActionTest() {
    assertTrue(evt4.getNewValue() instanceof EndTurnAction);
    actionControllerStub.setPhase(7);
    turnController.propertyChange(evt4);
  }

  /** Method startTurnActionTest tests StartTurnAction. */
  @Test
  @DisplayName("StartTurnAction test")
  public void startTurnActionTest() {
    assertTrue(evt1.getNewValue() instanceof StartTurnAction);
    assertTrue(evt2.getNewValue() instanceof StartTurnAction);
    assertTrue(evt3.getNewValue() instanceof StartTurnAction);
    turnController.propertyChange(evt1);
    controllerStub.getModel().getCurrentPlayer().getWorkers().get(0).setBlocked(true);
    turnController.propertyChange(evt3);
    controllerStub.getModel().getCurrentPlayer().getWorkers().get(0).setBlocked(false);
    turnController.propertyChange(evt3);
    actionControllerStub.setPhase(1);
    turnController.propertyChange(evt2);
    controllerStub.getModel().getCurrentPlayer().getWorkers().get(0).setBlocked(false);
    controllerStub.getModel().getCurrentPlayer().getWorkers().get(1).setBlocked(false);
    actionControllerStub.setPhase(0);
    controllerStub.getModel().setCurrentPlayer(piro);
    game.setCurrentPlayer(piro);
    turnController.propertyChange(evt13);
  }
  /** Method AthenaMovesTest tests Athena power active. */
  @Test
  @DisplayName("Testing Athena moves")
  public void AthenaMovesTest() {
    assertEquals("AthenaMovedUp", evt9.getNewValue());
    assertEquals("AthenaNormalMove", evt10.getNewValue());
    turnController.propertyChange(evt9);
    turnController.propertyChange(evt10);
  }
  /** Method actionsTest tests all possible actions. */
  @Test
  @DisplayName("Testing all actions")
  public void actionsTest() {
    assertTrue(evt5.getNewValue() instanceof SelectBuildAction);
    assertTrue(evt6.getNewValue() instanceof SelectMoveAction);
    assertTrue(evt11.getNewValue() instanceof SelectMoveAction);
    assertTrue(evt7.getNewValue() instanceof MoveAction);
    assertTrue(evt8.getNewValue() instanceof BuildAction);
    actionControllerStub.setPhase(2);
    game.setCurrentPlayer(ali);
    controllerStub.getModel().setCurrentPlayer(ali);
    game.getCurrentPlayer().getWorkers().get(0).setBlocked(false);
    game.getCurrentPlayer().getWorkers().get(1).setBlocked(false);
    actionControllerStub.setWorker(ali.getWorkers().get(0));
    turnController.propertyChange(evt5);
    actionControllerStub.setPhase(2);
    actionControllerStub.getWorker().setBlocked(true);
    turnController.propertyChange(evt5);
    turnController.propertyChange(evt6);
    turnController.propertyChange(evt7);
    turnController.propertyChange(evt8);
    actionControllerStub.setPhase(3);
    turnController.propertyChange(evt11);
  }
  /** Method unusedWorkerTest tests Ares power. */
  @Test
  @DisplayName("Testing Ares power")
  public void unusedWorkerTest() {
    game.getCurrentPlayer().getWorkers().get(0).setBlocked(false);
    game.getCurrentPlayer().getWorkers().get(1).setBlocked(false);
    controllerStub.getModel().setCurrentPlayer(ali);
    game.setCurrentPlayer(ali);
    actionControllerStub.setWorker(ali.getWorkers().get(0));
    assertEquals(ali.getWorkers().get(0), actionControllerStub.getWorker());
    actionControllerStub.setPhase(1);
    turnController.propertyChange(evt12);
  }
  /** Method endGameTest tests endGame method. */
  @Test
  @DisplayName("Testing end game")
  public void endGameTest() {
    actionControllerStub.setPhase(0);
    game.setCurrentPlayer(ali);
    controllerStub.getModel().setCurrentPlayer(ali);
    ali.getWorkers().get(0).setBlocked(true);
    ali.getWorkers().get(0).setPhases();
    assertEquals(Action.SELECT_MOVE, ali.getWorkers().get(0).getPhase(0).getAction());
    ali.getWorkers().get(1).setBlocked(true);
    ali.getWorkers().get(1).setPhases();
    turnController.propertyChange(evt2);
  }

  /** Class ActionControllerStub defines a stub for ActionController class. */
  public static class ActionControllerStub extends ActionController {
    private Worker worker;

    /**
     * Constructor ActionController creates a new ActionController instance.
     *
     * @param gameBoard of type GameBoard - GameBoard reference.
     */
    public ActionControllerStub(GameBoard gameBoard) {
      super(gameBoard);
      phase = 0;
      worker = null;
    }

    /**
     * Method getWorker returns the worker of this ActionController object.
     *
     * @return the worker (type Worker) of this ActionController object.
     * @see ActionController#getWorker()
     */
    @Override
    public Worker getWorker() {
      return worker;
    }

    /**
     * Method setPhase sets the phase of this ActionControllerStub object.
     *
     * @param phase the phase of this ActionControllerStub object.
     */
    public void setPhase(int phase) {
      if (phase < 0) {
        this.phase = Integer.parseInt(null);
      } else this.phase = phase;
    }
    /**
     * Method setWorker sets the worker of this ActionControllerStub object.
     *
     * @param worker the worker of this ActionControllerStub object.
     */
    public void setWorker(Worker worker) {
      this.worker = worker;
    }
    /**
     * Method readMessage notifies the player with the moves his worker can make.
     *
     * @param action of type SelectMoveAction - the action received from the client.
     * @return boolean false if the worker is blocked, or it isn't the correct phase of turn or
     *     gameBoard is null, true otherwise.
     * @see ActionController#readMessage(SelectMoveAction)
     */
    @Override
    public boolean readMessage(SelectMoveAction action) {
      return action.getMessage() == Action.SELECT_MOVE;
    }

    /**
     * Method readMessage notifies the player with a list of spaces in which his worker can build.
     *
     * @param action of type SelectBuildAction - action received from the client.
     * @return boolean false if it isn't the correct phase of the turn or gameBoard is null, true
     *     otherwise.
     * @see ActionController#readMessage(SelectBuildAction)
     */
    @Override
    public boolean readMessage(SelectBuildAction action) {
      System.out.println(" SelectBuildAction");
      return true;
    }

    /**
     * Method readMessage moves the worker into the space received.
     *
     * @param action of type MoveAction - the action received from teh server.
     * @return boolean false if it isn't the correct phase or if the worker cannot move into this
     *     space, true otherwise.
     * @see ActionController#readMessage(MoveAction)
     */
    @Override
    public boolean readMessage(MoveAction action) {
      System.out.println(" MoveAction");
      return true;
    }

    /**
     * Method readMessage builds the worker into the space received.
     *
     * @param action of type BuildAction - action received from teh server.
     * @return boolean false if it isn't the correct phase or if the worker cannot build into this
     *     space, true otherwise.
     * @see ActionController#readMessage(BuildAction)
     */
    @Override
    public boolean readMessage(BuildAction action) {
      System.out.println(" BuildAction");
      return true;
    }
  }

  /** Class GameHandlerStub defines a stub for GameHandler class */
  public class GameHandlerStub extends GameHandler {

    /**
     * Constructor GameHandler creates a new GameHandler instance.
     *
     * @param server of type Server - the main server class.
     */
    public GameHandlerStub(Server server) {
      super(server);
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
      String print;
      if (message.getMessage() == null) {
        print = "Start/End/Move/SelectBuild/BuildSelectMove action";
      } else print = message.getMessage().toString();
      System.out.println(print);
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
      return game.getCurrentPlayer().getClientID();
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
      System.out.println(message.getMessage());
    }
  }

  /** Class ControllerStub defines a stub for Controller class. */
  public static class ControllerStub extends Controller {

    /**
     * Constructor Controller creates a new Controller instance.
     *
     * @param model of type Game - Game reference.
     * @param gameHandler of type GameHandler - GameHandler reference.
     */
    public ControllerStub(Game model, GameHandler gameHandler) {
      super(model, gameHandler);
    }
  }
  /** Class PlayerStub defines a stub for Player class. */
  public static class PlayerStub extends Player {

    /**
     * Constructor PlayerStub creates a new PlayerStub instance.
     *
     * @param nickname of type String - the player's nickname.
     * @param clientID of type int - the clientID.
     */
    public PlayerStub(String nickname, int clientID) {
      super(nickname, clientID);
    }
  }
  /** Class ServerStub defines a stub for Server class. */
  public static class ServerStub extends Server {
    private HashMap<Integer, VirtualClient> idMapID;
    /**
     * Constructor Server creates the instance of the server, based on a socket and the mapping
     * between VirtualClient, nicknames and client ids. It also creates a new game session.
     */
    public ServerStub() {
      this.idMapID = null;
    }
    /**
     * Method setIdMapID sets the idMapID of this ServerStub object.
     *
     * @param idMapID the idMapID of this ServerStub object.
     */
    public void setIdMapID(HashMap idMapID) {
      this.idMapID = idMapID;
    }
    /**
     * Method getClientByID returns a link to the desired virtual client, in order to make
     * operations on it (like send, etc).
     *
     * @param id of type int - the id of the virtual client needed.
     * @return VirtualClient - the correct virtual client.
     * @see Server#getClientByID(int)
     */
    @Override
    public VirtualClient getClientByID(int id) {
      return idMapID.get(id);
    }
  }
  /** Class SocketClientConnectionStub defines a stub for SocketClientConnection class. */
  public static class SocketClientConnectionStub extends SocketClientConnection {

    /**
     * Constructor of the class: it instantiates an input/output stream from the socket received as
     * parameters, and add the main server to his attributes too.
     *
     * @param socket the socket which accepted the client connection.
     * @param server the main server class.
     */
    public SocketClientConnectionStub(Socket socket, Server server) {
      super(socket, server);
    }
    /**
     * Method close terminates the connection with the client, closing firstly input and output
     * streams, then invoking the server method called "unregisterClient", which will remove the
     * active virtual client from the list.
     *
     * @see Server#unregisterClient for more details.
     * @see SocketClientConnection#close()
     */
    @Override
    public void close() {
      System.out.println("Connection closed to client");
    }
  }
}
