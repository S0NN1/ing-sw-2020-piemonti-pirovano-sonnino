package it.polimi.ingsw.server.answers;

public class CustomMessage implements Answer {
    private String message;

    public CustomMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
