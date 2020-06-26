package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.player.Action;

/**
 * MoveAction class is a UserAction sent by the client to the server, it indicates a move action.
 *
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class MoveAction extends WorkerAction {

  private final Couple newPosition;
  private final Action action;

  /**
   * Constructor MoveAction creates a new MoveAction instance.
   *
   * @param row of type int - the row of the cell.
   * @param column of type int - the column of the cell.
   */
  public MoveAction(int row, int column) {
    newPosition = new Couple(row, column);
    action = Action.MOVE;
  }

  /**
   * Constructor MoveAction creates a new MoveAction instance.
   *
   * @param row of type int - the row of the cell.
   * @param column of type int - the column of the cell.
   * @param action of type Action - custom action.
   */
  public MoveAction(int row, int column, Action action) {
    newPosition = new Couple(row, column);
    this.action = action;
  }

  /**
   * Method getAction returns the action of this MoveAction object.
   *
   * @return the action (type Action) of this MoveAction object.
   */
  public Action getAction() {
    return action;
  }

  /** @see WorkerAction#getMessage() */
  @Override
  public Couple getMessage() {
    return newPosition;
  }
}
