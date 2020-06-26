package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.server.answers.Answer;

/**
 * LoseMessage class is an Answer used for sending the message "YOU LOSE" to a specific player.
 *
 * @author Alice Piemonti
 * @see Answer
 */
public class LoseMessage implements Answer {
  private final String winner;

  /**
   * Constructor LoseMessage creates a new LoseMessage instance.
   *
   * @param winner of type String - the winner's nickname.
   */
  public LoseMessage(String winner) {
    this.winner = winner;
  }

  /**
   * Method getWinner returns the winner of this LoseMessage object.
   *
   * @return the winner (type String) of this LoseMessage object.
   */
  public String getWinner() {
    return winner;
  }

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
