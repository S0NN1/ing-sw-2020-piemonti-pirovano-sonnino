package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

import java.util.List;

/**
 * Hephaestus class defines Hephaestus card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Hephaestus extends Worker {
  private Space oldPosition;

  /**
   * Constructor Worker creates a new Worker instance.
   *
   * @param color of type PlayerColors - the player's color.
   */
  public Hephaestus(PlayerColors color) {
    super(color);
  }

  /**
   * Method setPhases sets phases.
   *
   * @see Worker#setPhases()
   */
  @Override
  public void setPhases() {
    setTwoBuildPhases();
  }

  /**
   * Method getBuildableSpaces returns a List containing all the buildable spaces.
   *
   * @param gameBoard of type GameBoard - GameBoard reference.
   * @return List&lt;Space&gt; - the list of selectable spaces.
   * @see Worker#getBuildableSpaces(GameBoard)
   */
  @Override
  public List<Space> getBuildableSpaces(GameBoard gameBoard) {
    phases.get(5).changeMust(true);
    List<Space> result = super.getBuildableSpaces(gameBoard);
    if (result.isEmpty()) phases.get(5).changeMust(false);
    return result;
  }

  /**
   * Method canBuildOnto returns true if the worker can build into the space received.
   *
   * @param space of type Space - the space of provided.
   * @return boolean true if build is permitted, false otherwise.
   * @throws IllegalArgumentException when space is null.
   * @see Worker#canBuildOnto(Space)
   */
  @Override
  public boolean canBuildOnto(Space space) throws IllegalArgumentException {
    if (oldPosition == null) return super.canBuildOnto(space);
    return (oldPosition == space && space.getTower().getHeight() <= 2);
  }

  /**
   * Method build builds on the space received.
   *
   * @param space of type Space - the space provided.
   * @return boolean false if it's impossible to build on the space or if OutOfBoundException is
   *     thrown, true otherwise.
   * @throws IllegalArgumentException when space is null.
   * @see Worker#build(Space)
   */
  @Override
  public boolean build(Space space) throws IllegalArgumentException {
    if (super.build(space)) {
      if (oldPosition == null) { // first build
        oldPosition = space;
      } else oldPosition = null; // second build
      phases.get(5).changeMust(false);
      return true;
    }
    return false;
  }

  /**
   * Method notifyWithMoves notifies the selectSpacesListener with all the moves the worker can do.
   *
   * @param gameBoard of type GameBoard - GameBoard reference.
   * @throws IllegalArgumentException when gameBoard is null
   * @throws IllegalStateException when the worker is blocked
   * @see Worker#notifyWithMoves(GameBoard)
   */
  @Override
  public void notifyWithMoves(GameBoard gameBoard)
      throws IllegalArgumentException, IllegalStateException {
    oldPosition = null;
    super.notifyWithMoves(gameBoard);
  }
}
