package it.polimi.ingsw.server.answers;

public class CustomMessage implements Answer {
    private final String message;
    private final boolean input;

    public CustomMessage(String message, boolean input) {
        this.message = message;
        this.input = input;
    }

    public boolean canInput() {
        return input;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
