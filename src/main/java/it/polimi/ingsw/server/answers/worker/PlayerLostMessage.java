package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.server.answers.Answer;

/**
 * PlayerLostMessage class is an Answer used for sending the loser to all players.
 *
 * @author Luca Pirovano, Nicolò Sonnino
 * @see Answer
 */
public class PlayerLostMessage implements Answer {
  private final String loser;

  /**
   * Method getLoserColor returns the loserColor of this PlayerLostMessage object.
   *
   * @return the loserColor (type String) of this PlayerLostMessage object.
   */
  public String getLoserColor() {
    return loserColor;
  }

  private final String loserColor;

  /**
   * Constructor PlayerLostMessage creates a new PlayerLostMessage instance.
   *
   * @param loser of type String - the loser's nickname.
   * @param loserColor of type String - the loser's color.
   */
  public PlayerLostMessage(String loser, String loserColor) {
    this.loser = loser;
    this.loserColor = loserColor;
  }
  /**
   * Constructor PlayerLostMessage creates a new PlayerLostMessage instance.
   *
   * @param loser of type String - the loser's nickname.
   */
  public PlayerLostMessage(String loser) {
    this.loser = loser;
    this.loserColor = null;
  }
  /**
   * Method getLoser returns the loser of this PlayerLostMessage object.
   *
   * @return the loser (type String) of this PlayerLostMessage object.
   */
  public String getLoser() {
    return loser;
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
