package it.polimi.ingsw.server.answers;

import java.io.Serializable;

/**
 * Class SerializedAnswer is a Answer that can be serialized.
 *
 * @author Luca Pirovano
 * @see Serializable
 */
public class SerializedAnswer implements Serializable {
  private Answer serverAnswer;

  /**
   * Method setServerAnswer sets the serverAnswer of this SerializedAnswer object.
   *
   * @param answer the serverAnswer of this SerializedAnswer object.
   */
  public void setServerAnswer(Answer answer) {
    serverAnswer = answer;
  }

  /**
   * Method getServerAnswer returns the serverAnswer of this SerializedAnswer object.
   *
   * @return the serverAnswer (type Answer) of this SerializedAnswer object.
   */
  public Answer getServerAnswer() {
    return serverAnswer;
  }
}
