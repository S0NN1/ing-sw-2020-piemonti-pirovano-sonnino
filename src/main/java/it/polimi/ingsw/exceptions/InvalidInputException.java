package it.polimi.ingsw.exceptions;

public class InvalidInputException extends Exception {
    @Override
    public String getMessage() {
        return ("Error: Input must be between 0 and 24");
    }
}
