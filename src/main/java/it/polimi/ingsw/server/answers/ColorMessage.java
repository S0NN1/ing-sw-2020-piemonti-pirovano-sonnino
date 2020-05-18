package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.player.PlayerColors;

import java.util.ArrayList;
import java.util.List;

public class ColorMessage implements Answer {
    private final String message;
    private final String color;
    private List<PlayerColors> remaining = new ArrayList<>();

    public ColorMessage(String message) {
        this.message = message;
        this.color=null;
    }
    public ColorMessage(String message, String color) {
        this.message = message;
        this.color=color;
    }

    public String getColor() {
        return color;
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
