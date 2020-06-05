package it.polimi.ingsw.exceptions;

/**
 * Class DuplicateNicknameException is thrown when a user tries to connect with a nickname which is already registered
 * (and currently active) on the server process.
 *
 * @author Luca Pirovano
 */
public class DuplicateNicknameException extends Exception {
    /**
     * Method getMessage returns the message of this DuplicateNicknameException object.
     *
     * @return the message (type String) of this DuplicateNicknameException object.
     */
    @Override
    public String getMessage() {
        return "Error: this nickname has already been chosen!";
    }
}
