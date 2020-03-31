package it.polimi.ingsw.exceptions;

public class OutOfBoundException extends Exception{
    @Override
    public String getMessage() {
        return ("Error: Tower level not permitted");
    }

}
