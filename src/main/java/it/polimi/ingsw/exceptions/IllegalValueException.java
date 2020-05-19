package it.polimi.ingsw.exceptions;

public class IllegalValueException extends Exception {
    @Override
    public String getMessage() {
        return "Illegal value assigned";
    }
}
