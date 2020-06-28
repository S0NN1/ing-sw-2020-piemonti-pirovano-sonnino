package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.server.answers.Answer;

/**
 * EndTurnMessage class is an Answer used for notifying a client about the end of his/her turn.
 *
 * @author Nicolò Sonnino
 * @see Answer
 */
public class EndTurnMessage implements Answer {

  private final String message;

  /**
   * Constructor EndTurnMessage creates a new EndTurnMessage instance.
   *
   * @param message of type String - the message to be displayed.
   */
  public EndTurnMessage(String message) {
    this.message = message;
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
