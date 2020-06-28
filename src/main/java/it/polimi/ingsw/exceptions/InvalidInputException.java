package it.polimi.ingsw.exceptions;

/**
 * Class InvalidInputException is thrown when there's a try to insert on or access to a not existing
 * position on the game board.
 *
 * @author Luca Pirovano
 * @see Exception
 */
public class InvalidInputException extends Exception {
  /**
   * Method getMessage returns the message of this InvalidInputException object.
   *
   * @return the message (type String) of this InvalidInputException object.
   */
  @Override
  public String getMessage() {
    return ("Error: Input must be between 0 and 24");
  }
}
