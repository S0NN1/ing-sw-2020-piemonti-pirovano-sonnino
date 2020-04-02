package it.polimi.ingsw.server.answers;

public class ConnectionClosed implements Answer{
    private String message;

    public ConnectionClosed(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
