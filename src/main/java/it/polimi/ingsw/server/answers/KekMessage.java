package it.polimi.ingsw.server.answers;

public class KekMessage implements Answer{
    private static final String message = "lmaooooo";

    @Override
    public String getMessage() {
        return message;
    }
}
