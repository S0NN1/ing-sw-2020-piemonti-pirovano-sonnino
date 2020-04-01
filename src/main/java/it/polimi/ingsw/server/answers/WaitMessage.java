package it.polimi.ingsw.server.answers;

public class WaitMessage implements Answer {
    String message = "Server temporarily not available, please try again in a minute.";
    @Override
    public String getMessage() {
        return message;
    }
}
