package it.polimi.ingsw.exceptions;

/**
 * Class InvalidNicknameException is thrown where a user tries to connect with a nickname that
 * contains characters not allowed.
 *
 * @author Luca Pirovano
 */
public class InvalidNicknameException extends Exception {
  /**
   * Method getMessage returns the message of this InvalidNicknameException object.
   *
   * @return the message (type String) of this InvalidNicknameException object.
   */
  @Override
  public String getMessage() {
    return "Error: nickname can't contain - special character";
  }
}
