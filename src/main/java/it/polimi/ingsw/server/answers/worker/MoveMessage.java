package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;

/**
 * MoveMessage class is an Answer used for sending infos about a move action to the client.
 *
 * @author Alice Piemonti
 * @see Answer
 */
public class MoveMessage implements Answer {
  private final Move message;

  /**
   * Constructor MoveMessage creates a new MoveMessage instance.
   *
   * @param oldPosition of type Space - the old position.
   * @param newPosition of type Space - the new position.
   */
  public MoveMessage(Space oldPosition, Space newPosition) {
    message =
        new Move(
            oldPosition.getRow(),
            oldPosition.getColumn(),
            newPosition.getRow(),
            newPosition.getColumn());
  }

  /**
   * Method getMessage returns the message of this WorkerPlacement object.
   *
   * @return the message (type Object) of this WorkerPlacement object.
   * @see Answer#getMessage()
   */
  @Override
  public Move getMessage() {
    return message;
  }
}
