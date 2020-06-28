package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.answers.Answer;

/**
 * ModifiedTurnMessage class is an Answer used for sending a custom action of specific gods capable
 * of changing natural turn's phases.
 *
 * @author Nicolò Sonnino
 * @see Answer
 */
public class ModifiedTurnMessage implements Answer {
  private final String message;
  private final Action action;

  /**
   * Constructor ModifiedTurnMessage creates a new ModifiedTurnMessage instance.
   *
   * @param message of type String - the message to be displayed.
   */
  public ModifiedTurnMessage(String message) {
    this.message = message;
    this.action = null;
  }

  /**
   * Constructor ModifiedTurnMessage creates a new ModifiedTurnMessage instance.
   *
   * @param message of type String - the message to be displayed.
   * @param action of type Action - the type of action.
   */
  public ModifiedTurnMessage(String message, Action action) {
    this.message = message;
    this.action = action;
  }

  /**
   * Method getAction returns the action of this ModifiedTurnMessage object.
   *
   * @return the action (type Action) of this ModifiedTurnMessage object.
   */
  public Action getAction() {
    return action;
  }

  /**
   * Method getMessage returns the message of this WorkerPlacement object.
   *
   * @return the message (type Object) of this WorkerPlacement object.
   * @see Answer#getMessage()
   */
  @Override
  public String getMessage() {
    return message;
  }
}
