package it.polimi.ingsw.server.answers;

public class FullServer implements Answer{
    private static final String message = "This match is already full, please try again later!";

    @Override
    public String getMessage() {
        return message;
    }
}
