package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.answers.Answer;

/**
 * WinMessage class is an Answer used for sending win message to all players.
 *
 * @author Alice Piemonti
 * @see Answer
 */
public class WinMessage implements Answer {

  /**
   * Method getMessage returns the message of this WorkerPlacement object.
   *
   * @return the message (type Object) of this WorkerPlacement object.
   * @see Answer#getMessage()
   */
  @Override
  public Worker getMessage() {
    return null;
  }
}
