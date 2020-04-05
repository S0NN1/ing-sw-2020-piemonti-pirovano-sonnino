package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.player.PlayerColors;

import java.util.ArrayList;

public class RequestColor implements Answer {
    private final String message;
    private ArrayList<PlayerColors> remaining = new ArrayList<>();

    public RequestColor(String message) {
        this.message = message;
    }

    public void addRemaining(ArrayList<PlayerColors> colors) {
        remaining = colors;
    }

    public ArrayList<PlayerColors> getRemaining() {
        return remaining;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
