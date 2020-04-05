package it.polimi.ingsw.server.answers;

public class invalidInputRequest implements Answer {
    private final String message = "Invalid Input";
    @Override
    public Object getMessage() {
        return message;
    }
}
