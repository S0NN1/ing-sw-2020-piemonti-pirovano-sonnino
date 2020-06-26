package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PrometheusTest class tests Prometheus class.
 *
 * @author Alice Piemonti
 * @see Prometheus
 */
class PrometheusTest {

  Worker prometheus;
  GameBoard gameBoard;

  /** Method init initializes values. */
  @BeforeEach
  void init() {
    prometheus = new Prometheus(PlayerColors.RED);
    gameBoard = new GameBoard();
    prometheus.setPosition(gameBoard.getSpace(1, 1));
  }

  /**
   * Method moveBuild tests getPhase.isMust parameter during the succession of one move and one
   * build action in a single turn.
   */
  @Test
  @DisplayName("one move one build")
  void moveBuild() {
    // first turn
    assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(), "1");
    assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(), "2");

    prometheus.notifyWithMoves(gameBoard);
    Space space = gameBoard.getSpace(2, 2);
    assertTrue(prometheus.isSelectable(space), "3");
    assertTrue(prometheus.move(space), "4"); // move from 1,1 to 2,2

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "5");
    assertTrue(prometheus.build(space), "6"); // build in 1,1

    // second turn
    assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(), "7");
    assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(), "8");

    prometheus.notifyWithMoves(gameBoard);
    space = gameBoard.getSpace(1, 2);
    assertTrue(prometheus.isSelectable(space), "9");
    assertTrue(prometheus.move(space), "10"); // move from 2,2 to 1,2

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "11");
    assertTrue(prometheus.build(space), "12"); // build in 1,1
  }

  /**
   * Method moveUpBuild tests getPhase.isMust parameter during the succession of one move up and one
   * build action in a single turn.
   *
   * @throws OutOfBoundException when space is invalid.
   */
  @Test
  @DisplayName("one move up one build")
  void moveUpBuild() throws OutOfBoundException {
    // first turn
    assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(), "1");
    assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(), "2");

    prometheus.notifyWithMoves(gameBoard);
    Space space = gameBoard.getSpace(2, 2);
    space.getTower().addLevel();
    assertTrue(prometheus.isSelectable(space), "3");
    assertTrue(prometheus.move(space), "4"); // move up from 1,1 to 2,2
    assertEquals(1, prometheus.getPosition().getTower().getHeight(), "4a");

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "5");
    assertTrue(prometheus.build(space), "6"); // build in 1,1

    // second turn
    assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(), "7");
    assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(), "8");

    prometheus.notifyWithMoves(gameBoard);
    space = gameBoard.getSpace(1, 2);
    space.getTower().addLevel();
    space.getTower().addLevel();
    assertTrue(prometheus.isSelectable(space), "9");
    assertTrue(prometheus.move(space), "10"); // move up from 2,2 to 1,2
    assertEquals(2, prometheus.getPosition().getTower().getHeight(), "10a");

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "11");
    assertTrue(prometheus.build(space), "12"); // build in 1,1
  }

  /**
   * Method buildMoveBuild tests getPhase.isMust parameter during the succession of one build, one
   * move and another build action in a single turn; if the sequence of actions is correct:
   * Prometheus does not move up.
   */
  @Test
  @DisplayName("build move build")
  void buildMoveBuild() {
    // first turn
    assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(), "1");
    assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(), "2");

    prometheus.notifyWithBuildable(gameBoard);
    assertTrue(prometheus.getPhase(1).isMust(), "3");

    Space space = gameBoard.getSpace(1, 2);
    assertTrue(prometheus.canBuildOnto(space), "5");
    assertTrue(prometheus.build(space), "6"); // build in 1,2

    prometheus.notifyWithMoves(gameBoard);
    space = gameBoard.getSpace(2, 2);
    assertTrue(prometheus.isSelectable(space), "3");
    assertTrue(prometheus.move(space), "4"); // move from 1,1 to 2,2

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "5");
    assertTrue(prometheus.build(space), "6"); // build in 1,1

    // second turn
    assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(), "7");
    assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(), "8");

    prometheus.notifyWithMoves(gameBoard);
    space = gameBoard.getSpace(1, 2);
    assertTrue(prometheus.isSelectable(space), "9");
    assertTrue(prometheus.move(space), "10"); // move from 2,2 to 1,2

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "11");
    assertTrue(prometheus.build(space), "12"); // build in 1,1
  }

  /**
   * Method wrong tests getPhase.isMust parameter during the succession of one build, one move and
   * another build action in a single turn; if the sequence of actions is wrong: Prometheus tries to
   * move up.
   */
  @Test
  @DisplayName("build move up build")
  void wrong() {
    // first turn
    prometheus.notifyWithBuildable(gameBoard);

    Space space = gameBoard.getSpace(1, 2);
    assertTrue(prometheus.canBuildOnto(space), "1");
    assertTrue(prometheus.build(space), "2"); // build in 1,2

    prometheus.notifyWithMoves(gameBoard);
    space = gameBoard.getSpace(1, 2);
    assertFalse(prometheus.isSelectable(space), "3"); // try to move up from 1,1 to 1,2
    space = gameBoard.getSpace(2, 2);
    assertTrue(prometheus.isSelectable(space));
    assertTrue(prometheus.move(space)); // move from 1,1 to 2,2

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "5");
    assertTrue(prometheus.build(space), "6"); // build in 1,1

    // second turn
    assertTrue(!prometheus.getPhase(0).isMust() && !prometheus.getPhase(1).isMust(), "7");
    assertTrue(prometheus.getPhase(2).isMust() && prometheus.getPhase(3).isMust(), "8");

    prometheus.notifyWithMoves(gameBoard);
    space = gameBoard.getSpace(1, 2);
    assertTrue(prometheus.isSelectable(space), "9");
    assertTrue(prometheus.move(space), "10"); // move from 2,2 to 1,2

    prometheus.notifyWithBuildable(gameBoard);
    space = gameBoard.getSpace(1, 1);
    assertTrue(prometheus.canBuildOnto(space), "11");
    assertTrue(prometheus.build(space), "12"); // build in 1,1
  }
}
