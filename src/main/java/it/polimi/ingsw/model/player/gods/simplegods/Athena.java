package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * Athena class defines Athena card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Athena extends Worker {

  public static final String MOVE_UP_LISTENER = "moveUpListener";

  /**
   * Constructor Athena creates a new Athena instance.
   *
   * @param color of type PlayerColor - the player's color.
   * @param controller of type TurnController - TurnController reference.
   */
  public Athena(PlayerColors color, TurnController controller) {
    super(color);
    listeners.addPropertyChangeListener(MOVE_UP_LISTENER, controller);
  }

  /**
   * Method setPhases sets correct turn's phases.
   *
   * @see Worker#setPhases()
   */
  @Override
  public void setPhases() {
    setNormalPhases();
  }

  /**
   * Method move changes the worker's position while checking winning condition.
   *
   * @param space of type Space - the new position.
   * @return boolean false if the worker can't move into this space, true otherwise.
   * @throws IllegalArgumentException when space is null
   * @see Worker#move(Space)
   */
  @Override
  public boolean move(Space space) throws IllegalArgumentException {
    Space oldPosition = position;
    if (super.move(space)) {
      if (position.getTower().getHeight() - oldPosition.getTower().getHeight() == 1) {
        listeners.firePropertyChange(MOVE_UP_LISTENER, null, "AthenaMovedUp");
      } else listeners.firePropertyChange(MOVE_UP_LISTENER, null, "AthenaNormalMove");
      return true;
    }
    return false;
  }
}
