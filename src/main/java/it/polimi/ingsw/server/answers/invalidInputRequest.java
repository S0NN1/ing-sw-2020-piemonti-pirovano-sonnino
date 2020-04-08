package it.polimi.ingsw.server.answers;

/**
 * Answer sent by the server after client send an invalid input
 */
public class invalidInputRequest implements Answer {
    private final String message = "Invalid Input, try another move";
    @Override
    public Object getMessage() {
        return message;
    }
}
