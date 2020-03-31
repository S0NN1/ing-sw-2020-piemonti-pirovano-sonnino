package it.polimi.ingsw.server.answers;

public class ConnectionConfirmation implements Answer {
    private static final String message = "Connection was successfully set-up! You are now connected.";

    public String getMessage() {
        return message;
    }
}
