package it.polimi.ingsw.model.player;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.SerializedAnswer;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import it.polimi.ingsw.server.answers.worker.MoveMessage;
import it.polimi.ingsw.server.answers.worker.SelectSpacesMessage;
import it.polimi.ingsw.server.answers.worker.WinMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class WorkerTest tests Worker class.
 *
 * @author Alice Piemonti
 * @see Worker
 */
class WorkerTest {
  Worker worker;
  VirtualClientStub client;

  /** Method init initializes values. */
  @BeforeEach
  void init() {
    worker = new WorkerStub(PlayerColors.RED);
  }

  /** Method colorTest tests if player's colors are correctly displayed in CLI. */
  @Test
  @DisplayName("testing colors")
  void colorTest() {
    Worker workerBlue = new WorkerStub(PlayerColors.BLUE);
    Worker workerRed = new WorkerStub(PlayerColors.RED);
    Worker workerGreen = new WorkerStub(PlayerColors.GREEN);
    assertEquals("blue", workerBlue.getWorkerColor().toLowerCase());
    assertEquals("red", workerRed.getWorkerColor().toLowerCase());
    assertEquals("green", workerGreen.getWorkerColor().toLowerCase());
  }

  /**
   * Method constructorTest tests if the constructor set all values correctly, a worker must start
   * without a position and not blocked.
   */
  @Test
  @DisplayName("testing constructor")
  void constructorTest() {
    assertFalse(worker.isBlocked(), "if the worker isn't blocked");
    assertNull(worker.getPosition(), "if the worker hasn't got a position yet");
  }

  /**
   * Method setPositionTest tests the method setPosition.
   *
   * @throws InvalidInputException when the argument passed to the method is null.
   */
  @Test
  @DisplayName("setPosition method and exceptions")
  void setPositionTest() throws InvalidInputException {

    Space space = new Space();
    space.setRow(2);
    space.setColumn(1);
    worker.setPosition(space);
    int expX = 2;
    int expY = 1;
    assertEquals(expX, worker.getPosition().getRow());
    assertEquals(expY, worker.getPosition().getColumn());

    assertThrows(IllegalArgumentException.class, () -> worker.setPosition(null));
  }

  /**
   * Method moveTest tests test the method move, it must throw an IllegalArgumentException if the
   * argument passed is null. Sets the new position of the worker, the worker into space's attribute
   * "worker"and old space's attribute "worker" to null; finally tests win condition.
   */
  @Test
  @DisplayName("move method, exception and winning condition")
  void moveTest() {
    Space nullSpace = null;
    assertThrows(IllegalArgumentException.class, () -> worker.move(nullSpace));

    Space init = new Space();
    Space spaceFin = new Space();
    worker.setPosition(init);
    worker.move(spaceFin);

    assertEquals(spaceFin, worker.getPosition(), "1");
    assertNull(init.getWorker(), "2");
    assertEquals(worker, spaceFin.getWorker(), "3");

    Space space2 = new Space();
    Space space3 = new Space();
    space2.setTower(new Tower());

    for (int i = 0; i < 2; i++) {
      try {
        space2.getTower().addLevel();
      } catch (OutOfBoundException e) {
        e.printStackTrace();
      }
    }
    space3.setTower(new Tower());
    for (int i = 0; i < 3; i++) {
      try {
        space3.getTower().addLevel();
      } catch (OutOfBoundException e) {
        e.printStackTrace();
      }
    }

    System.out.println("you should win:");
    worker.move(space2);
    worker.move(space3);
  }

  /** Class VirtualClientStub defines a stub for VirtualClient class. */
  private static class VirtualClientStub extends VirtualClient {

    public boolean wins = false;
    private ArrayList<Couple> selectMoves;
    private Move move;
    private Couple build;
    private Worker winWorker;
    private boolean dome;

    /**
     * Method send prepares the answer for sending it through the network, putting it in a
     * serialized package, called SerializedMessage, then sends the packaged answer to the
     * transmission protocol, located in the socket-client handler.
     *
     * @see it.polimi.ingsw.server.SocketClientConnection for more details.
     * @param serverAnswer of type Answer - the answer to be sent to the user.
     * @see VirtualClient#send(Answer)
     */
    @Override
    public void send(Answer serverAnswer) {

      if (serverAnswer instanceof SelectSpacesMessage) {
        selectMoves = ((SelectSpacesMessage) serverAnswer).getMessage();
      } else if (serverAnswer instanceof MoveMessage) {
        move = ((MoveMessage) serverAnswer).getMessage();
      } else if (serverAnswer instanceof BuildMessage) {
        build = ((BuildMessage) serverAnswer).getMessage();
        dome = ((BuildMessage) serverAnswer).getDome();
      } else if (serverAnswer instanceof WinMessage) {
        winWorker = ((WinMessage) serverAnswer).getMessage();
      } else fail("unknown message");
    }

    /**
     * Method win sends the win confirmation to the winner and the lose communication to all the
     * others.
     *
     * @param win of type Answer - the message to be sent to the winner.
     * @see VirtualClient#win(Answer)
     */
    @Override
    public void win(Answer win) {
      SerializedAnswer winner = new SerializedAnswer();
      winner.setServerAnswer(win);
      wins = true;
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
      if (serverAnswer instanceof SelectSpacesMessage) {
        selectMoves = ((SelectSpacesMessage) serverAnswer).getMessage();
      } else if (serverAnswer instanceof MoveMessage) {
        move = ((MoveMessage) serverAnswer).getMessage();
      } else if (serverAnswer instanceof BuildMessage) {
        build = ((BuildMessage) serverAnswer).getMessage();
        dome = ((BuildMessage) serverAnswer).getDome();
      } else if (serverAnswer instanceof WinMessage) {
        winWorker = ((WinMessage) serverAnswer).getMessage();
      } else fail("unknown message");
    }

    /**
     * Method getSelectMoves returns the selectMoves of this VirtualClientStub object.
     *
     * @return the selectMoves (type Arraylist&lt;Couple&gt;) of this VirtualClientStub object.
     */
    public ArrayList<Couple> getSelectMoves() {
      return selectMoves;
    }

    /**
     * Method getMove returns the move of this VirtualClientStub object.
     *
     * @return the move (type Move) of this VirtualClientStub object.
     */
    public Move getMove() {
      return move;
    }

    /**
     * Method isDome returns the dome of this VirtualClientStub object.
     *
     * @return the dome (type boolean) of this VirtualClientStub object.
     */
    public boolean isDome() {
      return dome;
    }
  }

  /** Class WorkerStub defines a stub for Worker class. */
  public static class WorkerStub extends Worker {
    /**
     * Constructor Worker creates a new Worker instance.
     *
     * @param color of type PlayerColors - the player's color.
     */
    public WorkerStub(PlayerColors color) {
      super(color);
    }

    /**
     * Method setPhases sets phases.
     *
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
      setNormalPhases();
    }
  }

  /** Class getMovesTest tests the method selectMoves. */
  @Nested
  @DisplayName("getMoves tests")
  class getMovesTest {
    GameBoard gameBoard;

    /** Method init initializes values. */
    @BeforeEach
    void init() {
      gameBoard = new GameBoard();
    }

    /**
     * Method getMoves tests the return of a GameBoard without any tower, worker positioned in the
     * center of the GameBoard. Another worker positioned on borders and another one positioned on
     * corners.
     */
    @Test
    @DisplayName("without towers")
    void getMoves() {

      worker.setPosition(gameBoard.getSpace(2, 2));
      int expectedMovesCenter = 8;
      assertEquals(expectedMovesCenter, worker.selectMoves(gameBoard).size());

      int expectedMovesBorder = 5;
      worker.move(gameBoard.getSpace(0, 3));
      assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(), "1");
      worker.move(gameBoard.getSpace(4, 3));
      assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(), "2");
      worker.move(gameBoard.getSpace(3, 0));
      assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(), "3");
      worker.move(gameBoard.getSpace(1, 4));
      assertEquals(1, worker.getPosition().getRow(), "x");
      assertEquals(4, worker.getPosition().getColumn(), "y");
      assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(), "4");

      int expectedMovesCorner = 3;
      worker.move(gameBoard.getSpace(0, 0));
      assertEquals(expectedMovesCorner, worker.selectMoves(gameBoard).size(), "5");
      worker.move(gameBoard.getSpace(0, 4));
      assertEquals(expectedMovesCorner, worker.selectMoves(gameBoard).size(), "6");
      worker.move(gameBoard.getSpace(4, 0));
      assertEquals(expectedMovesCorner, worker.selectMoves(gameBoard).size(), "7");
      worker.move(gameBoard.getSpace(4, 4));
      assertEquals(
          expectedMovesCorner,
          worker.selectMoves(gameBoard).size(),
          "the worker moves " + "correctly without towers around");
    }

    /**
     * Method getMovesHeightTest tests the return of a GameBoard with towers of different height
     * around the worker and with worker positioned on towers with different height.
     *
     * @throws OutOfBoundException when too many levels are added.
     */
    @Test
    @DisplayName("from different height")
    void getMovesHeightTest() throws OutOfBoundException {

      gameBoard.getSpace(1, 1).getTower().addLevel();
      gameBoard.getSpace(1, 1).getTower().addLevel();
      gameBoard.getSpace(1, 2).getTower().addLevel();
      gameBoard.getSpace(2, 1).getTower().addLevel();
      gameBoard.getSpace(2, 1).getTower().addLevel();
      gameBoard.getSpace(2, 1).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(3, 1).getTower().addLevel();
      gameBoard.getSpace(3, 3).getTower().addLevel();
      gameBoard.getSpace(3, 3).getTower().addLevel();
      gameBoard.getSpace(3, 3).getTower().addLevel();

      int expected0 = 4;
      int expected1 = 5;
      int expected2 = 7;
      int expected3 = 7;

      worker.setPosition(gameBoard.getSpace(2, 2));
      assertEquals(expected0, worker.selectMoves(gameBoard).size());

      worker.move(gameBoard.getSpace(2, 2));
      gameBoard.getSpace(2, 2).getTower().addLevel();
      assertEquals(expected1, worker.selectMoves(gameBoard).size());

      worker.move(gameBoard.getSpace(2, 2));
      gameBoard.getSpace(2, 2).getTower().addLevel();
      assertEquals(expected2, worker.selectMoves(gameBoard).size());

      worker.move(gameBoard.getSpace(2, 2));
      gameBoard.getSpace(2, 2).getTower().addLevel();
      assertEquals(
          expected3,
          worker.selectMoves(gameBoard).size(),
          "the worker moves correctly from" + " any height to any height");
    }

    /**
     * Method getMovesButBlocked tests notifyWithMoves when worker is blocked it must throw an
     * IllegalStateException.
     *
     * @throws OutOfBoundException if too many levels are added
     */
    @Test
    @DisplayName("exception: worker blocked")
    void getMovesButBlocked() throws OutOfBoundException {
      GameBoard gameBoard = new GameBoard();
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          gameBoard.getSpace(i, j).setTower(new Tower());
          if (i != 1 || j != 1) {
            for (int k = 0; k < 4; k++) {
              gameBoard.getSpace(i, j).getTower().addLevel();
            }
          }
        }
      }
      worker.setPosition(gameBoard.getSpace(1, 1));
      assertThrows(IllegalStateException.class, () -> worker.notifyWithMoves(gameBoard));
    }

    /** Method getMovesNotMoveUp tests notifyWithMoves when worker can't move up. */
    @Test
    @DisplayName("worker can't move up")
    void getMovesNotMoveUp() {
      gameBoard = new GameBoard();
      worker.setPosition(gameBoard.getSpace(1, 1));
      worker.setCanMoveUp(false);
      for (int i = 0; i < 5; i++) {
        try {
          gameBoard.getSpace(i, 0).getTower().addLevel();
        } catch (OutOfBoundException e) {
          e.printStackTrace();
        }
      }

      assertEquals(5, worker.selectMoves(gameBoard).size(), "1");
    }
  }

  /** Class getBuildable tests the method getBuildableSpaces. */
  @Nested
  @DisplayName("getBuildableSpaces tests")
  class getBuildableSpaces {

    GameBoard gameBoard;

    /** Method init initializes values. */
    @BeforeEach
    void init() {
      gameBoard = new GameBoard();
      for (int i = 1; i < 4; i++) {
        for (int j = 1; j < 4; j++) {
          gameBoard.getSpace(i, j).setTower(new Tower());
        }
      }
    }

    /** Method centralBuild tests the worker positioned in the center of the GameBoard. */
    @Test
    @DisplayName("from central space")
    void centralBuild() {
      worker.setPosition(gameBoard.getSpace(1, 1));
      int expectedMoves = 8;
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
    }

    /** Method boundaryBuild tests the worker positioned on borders */
    @Test
    @DisplayName("from boundary space")
    void boundaryBuild() {
      int expectedMoves = 5;

      worker.setPosition(gameBoard.getSpace(0, 1));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
      worker.move(gameBoard.getSpace(4, 1));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
      worker.move(gameBoard.getSpace(1, 0));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
      worker.move(gameBoard.getSpace(1, 4));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
    }

    /** Method cornerBuild tests the worker positioned on corners. */
    @Test
    @DisplayName("from corners")
    void cornerBuild() {
      int expectedMoves = 3;

      worker.setPosition(gameBoard.getSpace(0, 0));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
      worker.setPosition(gameBoard.getSpace(4, 0));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
      worker.setPosition(gameBoard.getSpace(0, 4));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
      worker.setPosition(gameBoard.getSpace(4, 4));
      assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
    }

    /**
     * Method differentHeightBuild tests the worker positioned on a tower with different height and
     * with towers with different height around it.
     *
     * @throws OutOfBoundException when too many level are added.
     */
    @Test
    @DisplayName("from different heights")
    void differentHeightBuild() throws OutOfBoundException {
      gameBoard.getSpace(1, 1).getTower().addLevel();
      gameBoard.getSpace(1, 1).getTower().addLevel();
      gameBoard.getSpace(1, 2).getTower().addLevel();
      gameBoard.getSpace(2, 1).getTower().addLevel();
      gameBoard.getSpace(2, 1).getTower().addLevel();
      gameBoard.getSpace(2, 1).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(2, 3).getTower().addLevel();
      gameBoard.getSpace(3, 1).getTower().addLevel();
      gameBoard.getSpace(3, 3).getTower().addLevel();
      gameBoard.getSpace(3, 3).getTower().addLevel();
      gameBoard.getSpace(3, 3).getTower().addLevel();

      int expected = 7;

      worker.setPosition(gameBoard.getSpace(2, 2));
      assertEquals(expected, worker.getBuildableSpaces(gameBoard).size());

      worker.move(gameBoard.getSpace(2, 2));
      gameBoard.getSpace(2, 2).getTower().addLevel();
      assertEquals(expected, worker.getBuildableSpaces(gameBoard).size());

      worker.move(gameBoard.getSpace(2, 2));
      gameBoard.getSpace(2, 2).getTower().addLevel();
      assertEquals(expected, worker.getBuildableSpaces(gameBoard).size());

      worker.move(gameBoard.getSpace(2, 2));
      gameBoard.getSpace(2, 2).getTower().addLevel();
      assertEquals(
          expected,
          worker.getBuildableSpaces(gameBoard).size(),
          "the worker build correctly " + "from any height to any height");
    }
  }

  /** Class Listeners tests all the listeners associated to worker. */
  @Nested
  @DisplayName("listeners tests")
  class Listeners {

    GameBoard gameBoard;

    /**
     * Method init initializes values.
     *
     * @throws OutOfBoundException when position is invalid.
     */
    @BeforeEach
    void init() throws OutOfBoundException {
      gameBoard = new GameBoard();
      client = new VirtualClientStub();
      worker.createListeners(client);
      worker.setPosition(gameBoard.getSpace(3, 1));

      gameBoard.getSpace(3, 2).getTower().addLevel();
      gameBoard.getSpace(3, 2).getTower().addLevel();
    }

    /**
     * Method notifyWithMoves tests if the selectSpacesListener sends the message properly to the
     * virtual client which should receive a list of couples.
     */
    @Test
    @DisplayName("selectSpacesListener test")
    void selectSpacesListenerTest() {
      worker.notifyWithMoves(gameBoard);
      List<Space> moves = worker.selectMoves(gameBoard);
      for (int i = 0; i < moves.size(); i++) {
        assertEquals(moves.get(i).getRow(), client.getSelectMoves().get(i).getRow(), "x" + i);
        assertEquals(moves.get(i).getColumn(), client.getSelectMoves().get(i).getColumn(), "y" + i);
      }
      worker.notifyWithBuildable(gameBoard);
      List<Space> build = worker.getBuildableSpaces(gameBoard);
      for (int i = 0; i < build.size(); i++) {
        assertEquals(build.get(i).getRow(), client.getSelectMoves().get(i).getRow(), "x" + i);
        assertEquals(build.get(i).getColumn(), client.getSelectMoves().get(i).getColumn(), "y" + i);
      }
    }

    /**
     * Method moveListenerTest tests if the moveListener sends the old position and the new position
     * (Move type) properly to the virtual client during move method.
     */
    @Test
    @DisplayName("moveListener test")
    void moveListenerTest() {
      Space oldPosition = worker.getPosition();
      Space nextPosition = gameBoard.getSpace(3, 0);
      assertTrue(worker.isSelectable(nextPosition), "1");
      worker.move(nextPosition);
      assertEquals(oldPosition.getRow(), client.move.getOldPosition().getRow(), "2");
      assertEquals(oldPosition.getColumn(), client.move.getOldPosition().getColumn(), "3");
      assertEquals(nextPosition.getRow(), client.move.getNewPosition().getRow(), "4");
      assertEquals(nextPosition.getColumn(), client.move.getNewPosition().getColumn(), "5");
    }

    /**
     * Method buildListenerTest tests if the buildListener sends the position (Move type) where to
     * build properly to the virtual client during build method.
     */
    @Test
    @DisplayName("buildListener test")
    void buildListenerTest() {
      Space build = gameBoard.getSpace(3, 2); // build 3rd level
      assertTrue(worker.canBuildOnto(build), "1");
      worker.build(build);
      assertEquals(build.getRow(), client.build.getRow(), "2");
      assertEquals(build.getColumn(), client.build.getColumn(), "3");
      assertFalse(client.isDome());
    }

    /**
     * Method winListener tests if the winListener sends the current worker (this) properly to the
     * virtual client when winCondition method returns true.
     *
     * @throws OutOfBoundException when too many level are added.
     */
    @Test
    @DisplayName("winListener test")
    void winListener() throws OutOfBoundException {
      worker.getPosition().getTower().addLevel();
      worker.getPosition().getTower().addLevel(); // 2nd level
      Space nextPosition = gameBoard.getSpace(3, 2);
      nextPosition.getTower().addLevel(); // 3rd level
      assertTrue(worker.isSelectable(nextPosition), "1");
      worker.move(nextPosition);
      assertTrue(client.wins);
    }
  }
}
