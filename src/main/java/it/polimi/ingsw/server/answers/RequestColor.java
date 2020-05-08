package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.player.PlayerColors;

import java.util.ArrayList;
import java.util.List;

public class RequestColor implements Answer {
    private final String message;
    private List<PlayerColors> remaining = new ArrayList<>();

    public RequestColor(String message) {
        this.message = message;
    }

    public void addRemaining(List<PlayerColors> colors) {
        remaining = colors;
    }

    public List<PlayerColors> getRemaining() {
        return remaining;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
