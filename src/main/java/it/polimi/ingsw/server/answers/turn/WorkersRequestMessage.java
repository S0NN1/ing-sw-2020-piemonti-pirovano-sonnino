package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.server.answers.Answer;

/**
 * WorkersRequestMessage is an Answer used for requesting the worker intended to use during a turn.
 *
 * @author Nicolò Sonnino
 * @see Answer
 */
public class WorkersRequestMessage implements Answer {
  /**
   * Method getMessage returns the message of this WorkerPlacement object.
   *
   * @return the message (type Object) of this WorkerPlacement object.
   * @see Answer#getMessage()
   */
  @Override
  public Object getMessage() {
    return null;
  }
}
