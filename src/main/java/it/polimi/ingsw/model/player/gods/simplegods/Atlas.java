package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * Atlas class defines Atlas card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Atlas extends Worker {

  /**
   * Constructor Worker creates a new Worker instance.
   *
   * @param color of type PlayerColors - the player's color.
   */
  public Atlas(PlayerColors color) {
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

  /**
   * build a dome at any level or build a block
   *
   * @param buildDome of type boolean - a checker true if he wants to build a dome instead of a
   *     block, false otherwise.
   * @param space of type Space - the space provided.
   * @return Space - the space involved with the build action.
   * @see Worker#build(Space, boolean)
   */
  @Override
  public boolean build(Space space, boolean buildDome) throws IllegalArgumentException {
    if (space == null) throw new IllegalArgumentException();
    if (buildDome) {
      space.getTower().setDome(true);
      listeners.firePropertyChange("buildListener", true, space);
      return true;
    } else return super.build(space);
  }
}
