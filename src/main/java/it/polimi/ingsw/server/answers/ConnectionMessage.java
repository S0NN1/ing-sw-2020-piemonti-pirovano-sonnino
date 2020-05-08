package it.polimi.ingsw.server.answers;
//Connection was successfully set-up! You are now connected.
public class ConnectionMessage implements Answer {
    private final int type; //0: connection confirmation, 1: connection termination
    private final String message;

    public ConnectionMessage(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }
    public int getType() {
        return type;
    }
}
